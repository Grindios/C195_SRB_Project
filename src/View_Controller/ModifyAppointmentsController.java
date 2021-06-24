package View_Controller;

import DBAccess.DBAppointment;
import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import Interfaces.DateTimeInterface;
import LocaleFiles.LocaleInfo;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class ModifyAppointmentsController implements Initializable {
    @FXML
    ComboBox<String> contactCmb;
    @FXML
    ComboBox<Integer> startHourCmb;
    @FXML
    ComboBox<Integer> startMinuteCmb;
    @FXML
    ComboBox<Integer> endHourCmb;
    @FXML
    ComboBox<Integer> endMinuteCmb;
    @FXML
    TextField titleTxt;
    @FXML
    TextField locationTxt;
    @FXML
    TextField typeTxt;
    @FXML
    TextField descriptionTxt;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    ComboBox<String> customerCmb;
    @FXML
    private Label customerLbl;
    @FXML
    private Label titleLbl;
    @FXML
    private Label locationLbl;
    @FXML
    private Label contactLbl;
    @FXML
    private Label typeLbl;
    @FXML
    private Label descriptionLbl;
    @FXML
    private Label startDateLbl;
    @FXML
    private Label startTimeLbl;
    @FXML
    private Label endDateLbl;
    @FXML
    private Label endTimeLbl;
    @FXML
    private Button saveBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Label aptIDLbl;

    ResourceBundle rb = ResourceBundle.getBundle("LocaleFiles/Nat", LocaleInfo.getLocale());

    int selectedAppointmentID = AppointmentCalendarController.getSelectedAppointmentIndex();
    ObservableList<Integer> hour = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,00);
    ObservableList<Integer> minute = FXCollections.observableArrayList(00,15,30,45);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Loading ComboBox
        contactCmb.setItems(DBContacts.getAllcontacts());
        customerCmb.setItems(DBCustomers.getAllCustomers());
        startHourCmb.setItems(hour);
        startMinuteCmb.setItems(minute);
        endHourCmb.setItems(hour);
        endMinuteCmb.setItems(minute);


        // loading values
        // I need to get the selected value here.
        customerCmb.setValue(DBAppointment.getCustomerCmb(selectedAppointmentID));
        contactCmb.setValue(DBAppointment.getContactCmb(selectedAppointmentID));
        startHourCmb.setValue(DBAppointment.getStartHour(selectedAppointmentID));
        startMinuteCmb.setValue(DBAppointment.getStartMinute(selectedAppointmentID));
        endHourCmb.setValue(DBAppointment.getEndHour(selectedAppointmentID));
        endMinuteCmb.setValue(DBAppointment.getEndMinute(selectedAppointmentID));
        titleTxt.setText(DBAppointment.getTitleTxt(selectedAppointmentID));
        locationTxt.setText(DBAppointment.getLocationTxt(selectedAppointmentID));
        typeTxt.setText(DBAppointment.getTypeTxt(selectedAppointmentID));
        descriptionTxt.setText(DBAppointment.getDescriptionTxt(selectedAppointmentID));
        startDate.setValue(DBAppointment.getStartDate(selectedAppointmentID));
        endDate.setValue(DBAppointment.getEndDate(selectedAppointmentID));

        //Setting Language
        customerLbl.setText(rb.getString("ModAppointment.CustomerLbl"));
        titleLbl.setText(rb.getString("ModAppointment.TitleLbl"));
        locationLbl.setText(rb.getString("ModAppointment.LocationLbl"));
        contactLbl.setText(rb.getString("ModAppointment.ContactLbl"));
        typeLbl.setText(rb.getString("ModAppointment.TypeLbl"));
        descriptionLbl.setText(rb.getString("ModAppointment.DescriptionLbl"));
        startDateLbl.setText(rb.getString("ModAppointment.StartDateLbl"));
        startTimeLbl.setText(rb.getString("ModAppointment.StartTimeLbl"));
        endDateLbl.setText(rb.getString("ModAppointment.EndDateLbl"));
        endTimeLbl.setText(rb.getString("ModAppointment.EndTimeLbl"));
        saveBtn.setText(rb.getString("ModAppointment.saveBtn"));
        exitBtn.setText(rb.getString("ModAppointment.exitBtn"));
        backBtn.setText(rb.getString("ModAppointment.backBtn"));
        aptIDLbl.setText(rb.getString("ModAppointment.AppointmentIDLbl") + ": " + selectedAppointmentID);
    }

    public void ExitAct(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(rb.getString("ModAppointment.Alert.Exit.Title"));
        alert.setHeaderText(rb.getString("ModAppointment.Alert.Exit.Header"));
        alert.setContentText(rb.getString("ModAppointment.Alert.Exit.Content"));
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            Stage closeProgram = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            closeProgram.close();
        } else {
            alert.close();
        }
    }

    public void BackAct(ActionEvent actionEvent) throws IOException {
        Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentCalendar.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartsStage.setTitle(rb.getString("ModAppointment.Back.Title"));
        addPartsStage.setScene(addPartsScene);
        addPartsStage.show();
    }

    public void SaveAct(ActionEvent actionEvent) {
        try {
            // setting variables
            String customer = customerCmb.getValue();
            String title = titleTxt.getText();
            String location = locationTxt.getText();
            String contact = contactCmb.getValue();
            String type = typeTxt.getText();
            String description = descriptionTxt.getText();
            String error = "";
            int user_ID = LoginController.selectedUserIndex;

            // handeling of time
            int startHour = startHourCmb.getValue();
            int startMinute = startMinuteCmb.getValue();
            LocalDate startDt = startDate.getValue();
            int endHour = endHourCmb.getValue();
            int endMinute = endMinuteCmb.getValue();
            LocalDate endDt = endDate.getValue();


            //aggregates the dates
            DateTimeInterface timeInterface = (date, hr, min) ->  LocalDateTime.of(date.getYear(),date.getMonth(),date.getDayOfMonth(),hr,min);
            LocalDateTime aggStartDt = timeInterface.TimeAggregationAbsMeth(startDt, startHour, startMinute);
            LocalDateTime aggEndDt = timeInterface.TimeAggregationAbsMeth(endDt,endHour, endMinute);
            ZonedDateTime aggStartZDT = aggStartDt.atZone(ZoneId.systemDefault());
            ZonedDateTime aggEndZDT = aggEndDt.atZone(ZoneId.systemDefault());



            try {
                error = Appointment.validateAppointmentFields(title, description,location,contact,type,customer, error, aggStartZDT, aggEndZDT,startHour, startMinute, endHour, endMinute);
                error = error + DBAppointment.validateOverlappingTImesMod(aggStartDt,aggEndDt,selectedAppointmentID);
                if (error.length() > 0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(rb.getString("ModAppointment.Save.Error.Title"));
                    alert.setHeaderText(rb.getString("ModAppointment.Save.Error.Header"));
                    alert.setContentText(error);
                    alert.showAndWait();
                    System.out.println(error.length());
                    error = "";
                } else {
                    DBAppointment.modifyAppointment(title, description, location, contact, type, aggStartZDT, aggEndZDT, customer, user_ID, selectedAppointmentID);
                    Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentCalendar.fxml"));
                    Scene addPartsScene = new Scene(addPartsParent);
                    Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    addPartsStage.setScene(addPartsScene);
                    addPartsStage.show();

                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rb.getString("ModAppointment.Error.Title"));
                alert.setHeaderText(rb.getString("ModAppointment.Error.Header"));
                alert.setContentText(rb.getString("ModAppointment.Error.Content"));
                alert.showAndWait();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, rb.getString("ModAppointment.Error.EmptyFields"));
        }
    }

}

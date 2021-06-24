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

public class AddAppointmentController implements Initializable {
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

    ResourceBundle rb = ResourceBundle.getBundle("LocaleFiles/Nat", LocaleInfo.getLocale());
    /** This is the initialize method.
      It's called when the page is loaded.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Populate ComboBoxes
        startHourCmb.setItems(hour);
        startMinuteCmb.setItems(minute);
        endHourCmb.setItems(hour);
        endMinuteCmb.setItems(minute);
        contactCmb.setItems(DBContacts.getAllcontacts());
        customerCmb.setItems(DBCustomers.getAllCustomers());

        //Set Language
        customerLbl.setText(rb.getString("AddAppointment.CustomerLbl"));
        titleLbl.setText(rb.getString("AddAppointment.TitleLbl"));
        locationLbl.setText(rb.getString("AddAppointment.LocationLbl"));
        contactLbl.setText(rb.getString("AddAppointment.ContactLbl"));
        typeLbl.setText(rb.getString("AddAppointment.TypeLbl"));
        descriptionLbl.setText(rb.getString("AddAppointment.DescriptionLbl"));
        startDateLbl.setText(rb.getString("AddAppointment.StartDateLbl"));
        startTimeLbl.setText(rb.getString("AddAppointment.StartTimeLbl"));
        endDateLbl.setText(rb.getString("AddAppointment.EndDateLbl"));
        endTimeLbl.setText(rb.getString("AddAppointment.EndTimeLbl"));
        saveBtn.setText(rb.getString("AddAppointment.saveBtn"));
        exitBtn.setText(rb.getString("AddAppointment.exitBtn"));
        backBtn.setText(rb.getString("AddAppointment.backBtn"));

    }
    /**This is the exit method.
     When the exit button is clicked the form closes.*/
    public void ExitAct(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(rb.getString("AddAppointment.Alert.Exit.Title"));
        alert.setHeaderText(rb.getString("AddAppointment.Alert.Exit.Header"));
        alert.setContentText(rb.getString("AddAppointment.Alert.Exit.Content"));
        alert.showAndWait();
        if (alert.getResult()== ButtonType.OK) {
            Stage closeProgram = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            closeProgram.close();
        }
        else {
            alert.close();
        }
    }

    ObservableList<Integer> hour = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,00);
    ObservableList<Integer> minute = FXCollections.observableArrayList(00,15,30,45);
    /**This is the save method.
     When the user clicks the save button the contents of all the fields are passed to the DBAppointment save appointments mehtod.*/
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
            String overlapError = "";
            int user_ID = LoginController.selectedUserIndex;

            // handeling of time
            int startHour = startHourCmb.getValue();
            int startMinute = startMinuteCmb.getValue();
            LocalDate startDt = startDate.getValue();
            int endHour = endHourCmb.getValue();
            int endMinute = endMinuteCmb.getValue();
            LocalDate endDt = endDate.getValue();



            //Time aggregation
            //Lambda attempt
            DateTimeInterface timeInterface = (date, hr, min) ->  LocalDateTime.of(date.getYear(),date.getMonth(),date.getDayOfMonth(),hr,min);
            LocalDateTime aggStartDt = timeInterface.TimeAggregationAbsMeth(startDt,startHour,startMinute);
            LocalDateTime aggEndDt = timeInterface.TimeAggregationAbsMeth(endDt,endHour,endMinute);
            ZonedDateTime aggStartZDT = aggStartDt.atZone(ZoneId.systemDefault());
            ZonedDateTime aggEndZDT = aggEndDt.atZone(ZoneId.systemDefault());

            try {
                error = Appointment.validateAppointmentFields(title, description,location,contact,type,customer, error, aggStartZDT, aggEndZDT,startHour, startMinute, endHour, endMinute);
                error =  error + DBAppointment.validateOverlappingTImes(aggStartDt, aggEndDt);


                if (error.length() > 0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(rb.getString("AddAppointment.Save.Error.Title"));
                    alert.setHeaderText(rb.getString("AddAppointment.Save.Error.Header"));
                    alert.setContentText(error);
                    alert.showAndWait();
                    System.out.println(error.length());
                    error = "";

                } else {
                    DBAppointment.addAppointment(title, description, location, contact, type, aggStartZDT, aggEndZDT, customer, user_ID);
                    Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentCalendar.fxml"));
                    Scene addPartsScene = new Scene(addPartsParent);
                    Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    addPartsStage.setScene(addPartsScene);
                    addPartsStage.show();

                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rb.getString("AddAppointment.Error.Title"));
                alert.setHeaderText(rb.getString("AddAppointment.Error.Header"));
                alert.setContentText(rb.getString("AddAppointment.Error.Content"));
                alert.showAndWait();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, rb.getString("AddAppointment.Error.EmptyFields"));
        }

    }
    /**This is the back method.
     When the user clicks the back button, it takes the user to the previous page.*/
    public void BackAct(ActionEvent actionEvent) throws IOException {
        Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentCalendar.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartsStage.setScene(addPartsScene);
        addPartsStage.show();
    }
}

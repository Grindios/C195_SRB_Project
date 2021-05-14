package View_Controller;

import DBAccess.DBAppointment;
import DBAccess.DBContacts;
import DBAccess.DBCountries;
import DBAccess.DBCustomers;
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
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
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


    public void ExitAct(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Scheduler");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Press 'OK' to exit. \nPress Cancel to stay.");
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startHourCmb.setItems(hour);
        startMinuteCmb.setItems(minute);
        endHourCmb.setItems(hour);
        endMinuteCmb.setItems(minute);
        contactCmb.setItems(DBContacts.getAllcontacts());
        customerCmb.setItems(DBCustomers.getAllCustomers());
    }

    public void SaveAct(ActionEvent actionEvent) {
        // setting variables
        String customer = customerCmb.getValue();
        String title = titleTxt.getText();
        String location = locationTxt.getText();
        String contact = contactCmb.getValue();
        String type = typeTxt.getText();
        String description = descriptionTxt.getText();
        String error = "";
        Boolean valTxtFields = false;
        int user_ID = LoginController.selectedUserIndex;



        // handeling of time
        int startHour = startHourCmb.getValue();
        int startMinute = startMinuteCmb.getValue();
        LocalDate startDt = startDate.getValue();
        int endHour = endHourCmb.getValue();
        int endMinute = endMinuteCmb.getValue();
        Boolean valDateTime = false;
        Boolean valBusinessTime = false;
        LocalDate endDt = endDate.getValue();

        //aggregates the dates
        LocalDateTime aggStartDt = Appointment.TimeAggregation(startDt,startHour,startMinute);
        LocalDateTime aggEndDt = Appointment.TimeAggregation(endDt,endHour,endMinute);
        //validation calls
        valDateTime = Appointment.validateTimeRange(aggStartDt,aggEndDt);
        valBusinessTime = Appointment.validateBusinessHours(startHour, startMinute, endHour, endMinute);


        try {
        error = Appointment.validateTextFields(title, description,location,contact,type,customer, error);

        if (error.length()>0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Appointment schedule error");
            alert.setHeaderText("Appointment was not added.");
            alert.setContentText(error);
            alert.showAndWait();
            System.out.println(error.length());
            error = "";
        }
        else {
                DBAppointment.setAppointment(title,description,location,contact,type,aggStartDt,aggEndDt,customer,user_ID);
                //GOLD BABY!!!
                Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentCalendar.fxml"));
                Scene addPartsScene = new Scene(addPartsParent);
                Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                addPartsStage.setScene(addPartsScene);
                addPartsStage.show();

            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Scheduling appointment");
            alert.setContentText("Invalid Entry ");
            alert.showAndWait();
        }


        System.out.println(aggStartDt);
    }
}

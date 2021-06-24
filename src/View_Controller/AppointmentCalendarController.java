package View_Controller;

import DBAccess.DBAppointment;
import DBAccess.DBCustomers;
import LocaleFiles.LocaleInfo;
import Model.Appointment;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentCalendarController implements Initializable {
 // weekly columns
    @FXML
    private TableColumn<Appointment, String> aptIDCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, String> locationCol;
    @FXML
    private TableColumn<Appointment, String> contactCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, String> startCol;
    @FXML
    private TableColumn<Appointment, String> endCol;
    @FXML
    private TableColumn<Appointment, Integer> cusomterIDCol;

    //Buttons
    @FXML
    private Button allAppointmentBtn;
    @FXML
    private Button weeklyAptBtn;
    @FXML
    private Button monthlyAptBtn;
    @FXML
    private Button addAppointmentBtn;
    @FXML
    private Button modAppointmentBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private Button backBtn;


    //table
    @FXML
    private TableView<Appointment> appointmentsTbl;
    ResourceBundle rb = ResourceBundle.getBundle("LocaleFiles/Nat", LocaleInfo.getLocale());

    /**This is the initialize method.
     This method loads the the tables and labels with pertinent information.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set values
        aptIDCol.setCellValueFactory(new PropertyValueFactory<>("aptID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact_id"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        cusomterIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        updateAllAppointmentTBl();

        //setting language
        aptIDCol.setText(rb.getString("AptCalendar.AptIDCol"));
        titleCol.setText(rb.getString("AptCalendar.TitleCol"));
        descriptionCol.setText(rb.getString("AptCalendar.DescriptionCol"));
        locationCol.setText(rb.getString("AptCalendar.LocationCol"));
        contactCol.setText(rb.getString("AptCalendar.ContactCol"));
        typeCol.setText(rb.getString("AptCalendar.TypeCol"));
        startCol.setText(rb.getString("AptCalendar.StartTimeCol"));
        endCol.setText(rb.getString("AptCalendar.EndTimeCol"));
        cusomterIDCol.setText(rb.getString("AptCalendar.CustomerIDCol"));
        addAppointmentBtn.setText(rb.getString("AptCalendar.NewAppointmentBtn"));
        modAppointmentBtn.setText(rb.getString("AptCalendar.ModifyAppointmentBtn"));
        deleteBtn.setText(rb.getString("AptCalendar.DeleteAppointmentBtn"));
        allAppointmentBtn.setText(rb.getString("AptCalendar.AllAppointmentsBtn"));
        weeklyAptBtn.setText(rb.getString("AptCalendar.WeeklyAppointmentsBtn"));
        monthlyAptBtn.setText(rb.getString("AptCalendar.MonthlyAppointmentsBtn"));
        exitBtn.setText(rb.getString("AptCalendar.ExitBtn"));
        backBtn.setText(rb.getString("AptCalendar.backBtn"));

    }
    /**This is the weekly appointments method.
     It updates the table to view appointments by week when the weekly appointments button is clicked.*/
    @FXML
    public  void updateweekAppointmentsTbl() {appointmentsTbl.setItems(DBAppointment.getAppointmentsThisWeek());}
    /**This is the monthly appointments method.
     It updates the table to view appointments by month when the monthly appointment button is clicked.*/
    @FXML
    public void updatemonthAppointmentTbl() {appointmentsTbl.setItems(DBAppointment.getAppointmentsThisMonth());}
    /**This is the all appointment method.
     It update the table to view appointment by all of the existing appointments.*/
    @FXML
    public void updateAllAppointmentTBl() {appointmentsTbl.setItems(DBAppointment.getAllAppointments());}

    private static int selected_Apt = 0;
    /**This is the exit method.
     When the user clicks exit the form closes.*/
    public void ExitAct(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(rb.getString("AptCalendar.Alert.Exit.title"));
        alert.setHeaderText(rb.getString("AptCalendar.Alert.Exit.Header"));
        alert.setContentText(rb.getString("AptCalendar.Alert.Exit.Content"));
        alert.showAndWait();
        if (alert.getResult()== ButtonType.OK) {
            Stage closeProgram = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            closeProgram.close();
        }
        else {
            alert.close();
        }
    }
    /**This is the add appointment method.
     When the user clicks the add appointment button, it navigates to Add Appointments page.*/
    public void AddAppointmentAct(ActionEvent actionEvent) throws IOException {
        Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/AddAppointment.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartsStage.setScene(addPartsScene);
        addPartsStage.show();
    }


    /**This is the delete appointments method.
     When the user selects an appointment to delete, and presses the delete button, the appointment is deleted.*/
    @FXML
    public void DeleteAptAct(ActionEvent actionEvent) {
        Appointment appointment = appointmentsTbl.getSelectionModel().getSelectedItem();
        int apt_id = appointment.getAptID();
        String apt_Type = appointment.getType();
        Alert nullAlert = new Alert(Alert.AlertType.ERROR);
        nullAlert.setTitle(rb.getString("AptCalendar.Alert.Delete.Title"));
        nullAlert.setHeaderText(rb.getString("AptCalendar.Alert.Delete.Header"));
        nullAlert.setContentText(rb.getString("AptCalendar.Alert.Delete.Content1") + apt_id + rb.getString("AptCalendar.Alert.Delete.Content2") + apt_Type + rb.getString("AptCalendar.Alert.Delete.Content3") );
        nullAlert.showAndWait();
        DBAppointment.deleteAppointment(apt_id);
        updateAllAppointmentTBl();
    }


    /**This is the all appointments method
     It makes the call to get all the pertinent information to help load all appointments. */
    public void AllAppointmentsAct(ActionEvent actionEvent) {
        updateAllAppointmentTBl();
    }
    /**This is the weekly appointment method
     It makes the call to get the pertinent information to load weekly appointments*/
    public void WeeklyAppointmentsAct(ActionEvent actionEvent) {
        updateweekAppointmentsTbl();
    }

    /**This is the monthly appointment method
     It makes the call to get the pertinent information to load monthly appointments*/
    public void monthlyAppointmentAct(ActionEvent actionEvent) {
        updatemonthAppointmentTbl();
    }
    /**This is the modify appointments method.
     When clicked it navigates to the modify appointments page.*/
    public void ModAptAct(ActionEvent actionEvent) throws IOException {
        Appointment selectedCustomer = appointmentsTbl.getSelectionModel().getSelectedItem();


            if (selectedCustomer == null) {
                Alert nullAlert = new Alert(Alert.AlertType.ERROR);
                nullAlert.setTitle(rb.getString("AptCalendar.Alert.ModApt.Title"));
                nullAlert.setHeaderText(rb.getString("AptCalendar.Alert.ModApt.Header"));
                nullAlert.setContentText(rb.getString("AptCalendar.Alert.ModApt.Content"));
                nullAlert.showAndWait();
            }
            else {
                 selected_Apt = selectedCustomer.getAptID();
                    Parent modifyCustomerScreen = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyAppointments.fxml"));
                    Scene modifyCustomerScene = new Scene(modifyCustomerScreen);
                    Stage winModifyCustomer = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    winModifyCustomer.setTitle(rb.getString("AptCalendar.title.ModApt.Page"));
                    winModifyCustomer.setScene(modifyCustomerScene);
                    winModifyCustomer.show();

            }

    }
    /**This is the Selected appointment index.
     It assists with the selection of appointment to modify*/
    public static int getSelectedAppointmentIndex() {
        return selected_Apt; }
    /**This is the back method.
     When the back button is clicked it navigates to the previous page.*/
    public void BackAct(ActionEvent actionEvent) throws IOException {
        Parent modifyPartScreen = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerSelection.fxml"));
        Scene modifyPartScene = new Scene(modifyPartScreen);
        Stage winModifyPart = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        winModifyPart.setTitle(rb.getString("AddAppointment.Back.Title"));
        winModifyPart.setScene(modifyPartScene);
        winModifyPart.show();
    }
}

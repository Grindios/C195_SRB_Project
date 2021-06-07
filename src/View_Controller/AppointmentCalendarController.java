package View_Controller;

import DBAccess.DBAppointment;
import DBAccess.DBCustomers;
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
    private TableView<Appointment> appointmentsTbl;

    @FXML
    public  void updateweekAppointmentsTbl() {appointmentsTbl.setItems(DBAppointment.getAppointmentsThisWeek());}
    @FXML
    public void updatemonthAppointmentTbl() {appointmentsTbl.setItems(DBAppointment.getAppointmentsThisMonth());}
    @FXML
    public void updateAllAppointmentTBl() {appointmentsTbl.setItems(DBAppointment.getAllAppointments());}

    private static int selected_Apt = 0;

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
    public void AddAppointmentAct(ActionEvent actionEvent) throws IOException {
        Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/AddAppointment.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartsStage.setScene(addPartsScene);
        addPartsStage.show();
    }



    @FXML
    public void DeleteAptAct(ActionEvent actionEvent) {
        Appointment appointment = appointmentsTbl.getSelectionModel().getSelectedItem();
        int apt_id = appointment.getAptID();
        DBAppointment.deleteAppointment(apt_id);
        updateAllAppointmentTBl();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //week view
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



    }

    public void AllAppointmentsAct(ActionEvent actionEvent) {
        updateAllAppointmentTBl();
    }

    public void WeeklyAppointmentsAct(ActionEvent actionEvent) {
        updateweekAppointmentsTbl();
    }


    public void monthlyAppointmentAct(ActionEvent actionEvent) {
        updatemonthAppointmentTbl();
    }

    public void ModAptAct(ActionEvent actionEvent) throws IOException {
        Appointment selectedCustomer = appointmentsTbl.getSelectionModel().getSelectedItem();


            if (selectedCustomer == null) {
                Alert nullAlert = new Alert(Alert.AlertType.ERROR);
                nullAlert.setTitle("Error");
                nullAlert.setHeaderText("Appointment Not selected");
                nullAlert.setContentText("Please select an appointment");
                nullAlert.showAndWait();
            }
            else {
                 selected_Apt = selectedCustomer.getAptID();
                    Parent modifyPartScreen = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyAppointments.fxml"));
                    Scene modifyPartScene = new Scene(modifyPartScreen);
                    Stage winModifyPart = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    winModifyPart.setTitle("Modify Appointment");
                    winModifyPart.setScene(modifyPartScene);
                    winModifyPart.show();

            }

    }
    public static int getSelectedAppointmentIndex() {
        return selected_Apt; }
}

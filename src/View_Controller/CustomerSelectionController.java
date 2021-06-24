package View_Controller;

import DBAccess.DBAppointment;
import DBAccess.DBCustomers;
import Database.DBConnection;
import LocaleFiles.LocaleInfo;
import Model.Customer;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerSelectionController implements Initializable {

@FXML
private TableView<Customer> CustomerTable;
@FXML
private TableColumn<Customer, String > custCol;
@FXML
private TableColumn<Customer, String> addyCol;
@FXML
private TableColumn<Customer, String> phoneCol;
@FXML
private TableColumn<Customer, Integer> idCol;
@FXML
private Button modifyBtn;
@FXML
private Button newCustomerBtn;
@FXML
private Button deleteBtn;
@FXML
private Button appointmentsBtn;
@FXML
private Button exitBtn;
@FXML
private Button signOutBtn;
@FXML
private Button reportBtn;


Boolean alert = DBAppointment.upcomingAppointment();
private static int selectedCustomerIndex = 0;
    ResourceBundle rb = ResourceBundle.getBundle("LocaleFiles/Nat", LocaleInfo.getLocale());
    /**This is the initialize method.
     it loads the all the pertinent information to the page.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set column values
        custCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        addyCol.setCellValueFactory(new PropertyValueFactory<>("addy"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        updateCustomerTbl();
        System.out.println("Welcome " + LoginController.selectedUserIndex+"!");


        //set language labels"
        custCol.setText(rb.getString("SelectCustomer.custCol"));
        addyCol.setText(rb.getString("SelectCustomer.addyCol"));
        phoneCol.setText(rb.getString("SelectCustomer.phoneCol"));
        idCol.setText(rb.getString("SelectCustomer.idCol"));
        modifyBtn.setText(rb.getString("SelectCustomer.modifyBtn"));
        newCustomerBtn.setText(rb.getString("SelectCustomer.newCustomerBtn"));
        deleteBtn.setText(rb.getString("SelectCustomer.deleteBtn"));
        appointmentsBtn.setText(rb.getString("SelectCustomer.appointmentsBtn"));
        exitBtn.setText(rb.getString("SelectCustomer.exitBtn"));
        signOutBtn.setText(rb.getString("SelectCustomer.signOutBtn"));
        reportBtn.setText(rb.getString("SelectCustomer.ReportBtn"));

        //check for appointments
        if (alert) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(rb.getString("SelectCustomer.Alert.Title"));
            alert.setHeaderText(rb.getString("SelectCustomer.Alert.UpcomingApts"));
            alert.setContentText(rb.getString("SelectCustomer.Alert.AptID") + DBAppointment.appointmentIndex + rb.getString("SelectCustomer.Alert.StartTime") + DBAppointment.stringDateTimePublic);
            alert.showAndWait();
            System.out.println(alert);
        }
        else {
            JOptionPane.showMessageDialog(null, rb.getString("SelectCustomer.Alert.NoUpcomingApts"));
            System.out.println(alert);
        }
    }
    /**This is the new customer method.
     When the new customer button is clicked it navigates to the new customer page*/
    public void NewCustomerAct(ActionEvent actionEvent) throws IOException {

        Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/AddCustomer.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartsStage.setScene(addPartsScene);
        addPartsStage.show();

        //clear customer table
        CustomerTable.setItems(null);
    }
    /**This is the Exit customer action.
     When the Exit button is clicked it closes the form.*/
    public void ExitCustomerAct(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(rb.getString("SelectCustomer.Alert.exit.title"));
        alert.setHeaderText(rb.getString("SelectCustomer.Alert.exit.header"));
        alert.setContentText(rb.getString("SelectCustomer.Alert.exit.content"));
        alert.showAndWait();
        if (alert.getResult()== ButtonType.OK) {
            Stage closeProgram = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            closeProgram.close();
        }
        else {
            alert.close();
        }
    }
    /**This is the Sign out button.
     When the sign out button is clicked it navigates to the login page*/
    public void SignOutAct(ActionEvent actionEvent) {
        Parent addPartsParent = null;


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(rb.getString("SelectCustomer.Alert.signout.title"));
        alert.setHeaderText(rb.getString("SelectCustomer.Alert.signout.header"));
        alert.setContentText(rb.getString("SelectCustomer.Alert.signout.text"));
        alert.showAndWait();
        if(alert.getResult()==ButtonType.OK) {
            JOptionPane.showMessageDialog(null, rb.getString("SelectCustomer.Alert.signout.bye"));
            try {
                addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/Login.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene addPartsScene = new Scene(addPartsParent);
            Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            addPartsStage.setScene(addPartsScene);
            addPartsStage.show();
        }
        else {
            alert.close();
        }
    }



    /**This is the Appointments method.
     When the appointments button is clicked it navigates to the appointments page*/
    public void AppointmentsAct(ActionEvent actionEvent) throws IOException {
        Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentCalendar.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartsStage.setScene(addPartsScene);
        addPartsStage.show();
    }
    /**This is the delete method.
     When the delete button is clicked the selected customer is deleted as long as there isn't an appointment assigned to the customer.*/
    public void deleteAct(ActionEvent actionEvent) {

        Customer customer = CustomerTable.getSelectionModel().getSelectedItem();
        String check_id = Integer.toString(customer.getId());
        String name = customer.getCustomer();
        String checked = DBCustomers.doesAppointmentExist(check_id);


        if (checked.isBlank()) {
            JOptionPane.showMessageDialog(null, name +" " + rb.getString("SelectCustomer.Alert.delete.deleted"));
            DBCustomers.deleteCustomer(check_id);
        }
        else {
            JOptionPane.showMessageDialog(null, name +" " + rb.getString("SelectCustomer.Alert.delete.cannot_delete"));

        }
        updateCustomerTbl();
    }
    /**This is the modify customer method.
     When the modify customer button is clicked, the selected customer is modified.*/
    public void ModifyCustomerAct(ActionEvent actionEvent) throws IOException {

        Customer selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();

            if (selectedCustomer == null) {
                Alert nullAlert = new Alert(Alert.AlertType.ERROR);
                nullAlert.setTitle(rb.getString("SelectCustomer.Alert.mod.title"));
                nullAlert.setHeaderText(rb.getString("SelectCustomer.Alert.mod.header"));
                nullAlert.setContentText(rb.getString("SelectCustomer.Alert.mod.content"));
                nullAlert.showAndWait();
            } else {

                selectedCustomerIndex = selectedCustomer.getId();

                System.out.println("Attempting to modify");
                Parent modifyPartScreen = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyCustomer.fxml"));
                Scene modifyPartScene = new Scene(modifyPartScreen);
                Stage winModifyPart = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                winModifyPart.setTitle(rb.getString("SelectCustomer.Alert.mod.page_title"));
                winModifyPart.setScene(modifyPartScene);
                winModifyPart.show();
            }

        }

    /**This is the selected customer index method.
     This method helps with the selection of customers to modify the selected user.*/
    public static int getSelectedCustomerIndex() {
        return selectedCustomerIndex;
    }
    /**This is the update customer method.
     This method updates the customer table with the pertinent information.*/
    @FXML
    public void updateCustomerTbl() {CustomerTable.setItems(DBCustomers.getCustomers());}

    /**This is the report method.
     When the user clicks on the report button it navigates to the reports page.*/
    public void ReportAct(ActionEvent actionEvent) throws IOException {
        Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/Report.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartsStage.setTitle(rb.getString("SelectCustomer.ReportNav"));
        addPartsStage.setScene(addPartsScene);
        addPartsStage.show();
    }
}

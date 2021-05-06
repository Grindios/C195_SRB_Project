package View_Controller;

import DBAccess.DBCustomers;
import Database.DBConnection;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void NewCustomerAct(ActionEvent actionEvent) throws IOException {

        Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/AddCustomer.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartsStage.setScene(addPartsScene);
        addPartsStage.show();

        //clear customer table
        CustomerTable.setItems(null);
    }

    public void ExitCustomerAct(ActionEvent actionEvent) {
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

    public void SignOutAct(ActionEvent actionEvent) {
        Parent addPartsParent = null;


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Signing out of Scheduler");
        alert.setHeaderText("Are you sure you want to sign out?");
        alert.setContentText("Press 'OK' to sign off. \nPress Cancel to stay.");
        alert.showAndWait();
        if(alert.getResult()==ButtonType.OK) {
            JOptionPane.showMessageDialog(null, "Goodbye");
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

    @FXML
    public void updateCustomerTbl() {CustomerTable.setItems(DBCustomers.getCustomers());}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        addyCol.setCellValueFactory(new PropertyValueFactory<>("addy"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        updateCustomerTbl();
    }


    public void AppointmentsAct(ActionEvent actionEvent) throws IOException {
        Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentCalendar.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartsStage.setScene(addPartsScene);
        addPartsStage.show();
    }
}

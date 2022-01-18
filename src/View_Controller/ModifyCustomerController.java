package View_Controller;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBFirstLvlDivision;
import Interfaces.DateTimeInterface;
import LocaleFiles.LocaleInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {


    @FXML
    TextField nameTxt;
    @FXML
    TextField addressTxt;
    @FXML
    ComboBox<String> countryCmb;
    @FXML
    ComboBox<String> divisionCmb;
    @FXML
    TextField postalTxt;
    @FXML
    TextField phoneTxt;
    @FXML
    private javafx.scene.control.Label customerLbl;
    @FXML
    private javafx.scene.control.Label addyLbl;
    @FXML
    private javafx.scene.control.Label countryLbl;
    @FXML
    private javafx.scene.control.Label divisionLbl;
    @FXML
    private javafx.scene.control.Label postalLbl;
    @FXML
    private javafx.scene.control.Label phoneLbl;
    @FXML
    private javafx.scene.control.Label customerIDLbl;
    @FXML
    private javafx.scene.control.Label CUSTOMERIDLBL;
    @FXML
    private javafx.scene.control.Button backBtn;
    @FXML
    private javafx.scene.control.Button saveBtn;
    @FXML
    private javafx.scene.control.Button exitBtn;

    ResourceBundle rb = ResourceBundle.getBundle("LocaleFiles/Nat", LocaleInfo.getLocale());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load values
        countryCmb.setItems(DBCountries.getAllCountries());
        nameTxt.setText(DBCustomers.getCustomerName(selectedCustomerID));
        addressTxt.setText(DBCustomers.getCustomerAddress(selectedCustomerID));
        countryCmb.setValue(DBCustomers.getCountry(selectedCustomerID));
        divisionCmb.setValue(DBCustomers.getDivision(selectedCustomerID));
        postalTxt.setText(DBCustomers.getPostalCode(selectedCustomerID));
        phoneTxt.setText(DBCustomers.getPhone(selectedCustomerID));
        CUSTOMERIDLBL.setText(selectedCustomerID);

        // set Language
        backBtn.setText(rb.getString("ModifyCustomer.backBtn"));
        saveBtn.setText(rb.getString("ModifyCustomer.saveBtn"));
        exitBtn.setText(rb.getString("ModifyCustomer.exitBtn"));
        customerLbl.setText(rb.getString("ModifyCustomer.customerLbl"));
        addyLbl.setText(rb.getString("ModifyCustomer.addyLbl"));
        countryLbl.setText(rb.getString("ModifyCustomer.countryLbl"));
        divisionLbl.setText(rb.getString("ModifyCustomer.divisionLbl"));
        postalLbl.setText(rb.getString("ModifyCustomer.postalLbl"));
        phoneLbl.setText(rb.getString("ModifyCustomer.phoneLbl"));


    }

    String selectedCustomerID = Integer.toString(CustomerSelectionController.getSelectedCustomerIndex());

    public void FilterFrstLvlDivAct(MouseEvent mouseEvent) {
        String selectedCountry;
        selectedCountry = countryCmb.getValue();
        divisionCmb.setItems(DBFirstLvlDivision.filterFirstLvlDiv(selectedCountry));
    }

    public void CountryCmdAct(MouseEvent actionEvent) {
        countryCmb.setItems(DBCountries.getAllCountries());
    }

    public void GoBackAct(ActionEvent actionEvent) throws IOException {
        Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerSelection.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartsStage.setScene(addPartsScene);
        addPartsStage.show();
    }

    public void SaveAct(ActionEvent actionEvent) throws IOException {
        int cID = Integer.parseInt(selectedCustomerID);
        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String phone = phoneTxt.getText();
        String country = countryCmb.getValue();
        String division = divisionCmb.getValue();
        String postal = postalTxt.getText();
        String valMessage = new String();
        try {

            valMessage = Model.Customer.entryValidation(name,address,phone,division,postal,valMessage);
            if(valMessage.length()>0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rb.getString("ModifyCustomer.Alert.Save.title"));
                alert.setHeaderText(rb.getString("ModifyCustomer.Alert.Save.header"));
                alert.setContentText(valMessage);
                alert.showAndWait();
                valMessage = "";
            }
            else {
                DBCustomers.modifyCustomers(name, address, postal, phone, division, cID);

                Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerSelection.fxml"));
                Scene addPartsScene = new Scene(addPartsParent);
                Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                addPartsStage.setScene(addPartsScene);
                addPartsStage.show();
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }



    }

    public void ExitAct(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(rb.getString("ModifyCustomer.Alert.Exit.Title"));
        alert.setHeaderText(rb.getString("ModifyCustomer.Alert.Exit.header"));
        alert.setContentText(rb.getString("ModifyCustomer.Alert.Exit.content"));
        alert.showAndWait();
        if (alert.getResult()== ButtonType.OK) {
            Stage closeProgram = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            closeProgram.close();
        }
        else {
            alert.close();
        }
    }


}

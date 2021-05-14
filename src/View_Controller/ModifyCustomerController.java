package View_Controller;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBFirstLvlDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
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


    String selectedCustomerID = Integer.toString(CustomerSelectionController.getSelectedCustomerIndex());



    public void FilterFrstLvlDivAct(MouseEvent mouseEvent) {
        String selectedCountry;
        selectedCountry = countryCmb.getValue();
        divisionCmb.setItems(DBFirstLvlDivision.filterFirstLvlDiv(selectedCountry));
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


        DBCustomers.modifyCustomers(name, address, postal, phone, division, cID);

        Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerSelection.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartsStage.setScene(addPartsScene);
        addPartsStage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCmb.setItems(DBCountries.getAllCountries());
        nameTxt.setText(DBCustomers.getCustomerName(selectedCustomerID));
        addressTxt.setText(DBCustomers.getCustomerAddress(selectedCustomerID));
        countryCmb.setValue(DBCustomers.getCountry(selectedCustomerID));
        divisionCmb.setValue(DBCustomers.getDivision(selectedCustomerID));
        postalTxt.setText(DBCustomers.getPostalCode(selectedCustomerID));
        phoneTxt.setText(DBCustomers.getPhone(selectedCustomerID));


    }
}

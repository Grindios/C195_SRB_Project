package View_Controller;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBFirstLvlDivision;
import LocaleFiles.LocaleInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    @FXML
    ComboBox<String> countryCmb;
    @FXML
    ComboBox<String> divisionCmb;
    @FXML
    private Button saveBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private Button backBtn;
    @FXML
    TextField customerNameTxt;
    @FXML
    TextField phoneNumTxt;
    @FXML
    TextField addressTxt;
    @FXML
    TextField zipTxt;
    @FXML
    private Label customerLbl;
    @FXML
    private Label addressLbl;
    @FXML
    private Label divisionLbl;
    @FXML
    private Label postalLbl;
    @FXML
    private Label countryLbl;
    @FXML
    private Label phoneLbl;

    ResourceBundle rb = ResourceBundle.getBundle("LocaleFiles/Nat", LocaleInfo.getLocale());
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Populating country ComboBox
        countryCmb.setItems(DBCountries.getAllCountries());

        //Setting Language
        customerLbl.setText(rb.getString("AddCustomer.customerLbl"));
        addressLbl.setText(rb.getString("AddCustomer.addyLbl"));
        postalLbl.setText(rb.getString("AddCustomer.postalLbl"));
        divisionLbl.setText(rb.getString("AddCustomer.divisionLbl"));
        countryLbl.setText(rb.getString("AddCustomer.countryLbl"));
        phoneLbl.setText(rb.getString("AddCustomer.phoneLbl"));
        saveBtn.setText(rb.getString("AddCustomer.saveBtn"));
        backBtn.setText(rb.getString("AddCustomer.backBtn"));
        exitBtn.setText(rb.getString("AddCustomer.exitBtn"));



    }

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
        String name = customerNameTxt.getText();
        String address = addressTxt.getText();
        String phone = phoneNumTxt.getText();
        String country = countryCmb.getValue();
        String division = divisionCmb.getValue();
        String postal = zipTxt.getText();
        String valMessage = new String();
        try {
            valMessage = Model.Customer.entryValidation(name,address,phone,division,postal,valMessage);
            if(valMessage.length()>0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rb.getString("AddCustomer.Alert.Save.title"));
                alert.setHeaderText(rb.getString("AddCustomer.Alert.Save.header"));
                alert.setContentText(valMessage);
                alert.showAndWait();
                valMessage = "";
            }
            else {

                DBCustomers.setCustomers(name, address, postal, phone, division);

                Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerSelection.fxml"));
                Scene addPartsScene = new Scene(addPartsParent);
                Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                addPartsStage.setScene(addPartsScene);

                addPartsStage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }






    public void ExitBtn(ActionEvent actionEvent) {
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

package View_Controller;

import DBAccess.DBCountries;
import DBAccess.DBFirstLvlDivision;
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
    Button saveBtn;
    @FXML
    TextField customerNameTxt;
    @FXML
    TextField addressTxt;
    @FXML
    TextField zipTxt;
    @FXML
    TextField phoneTxt;

    private String catchError = new String();
    public void FilterFrstLvlDivAct(MouseEvent mouseEvent) {
        String selectedCountry;
        selectedCountry = countryCmb.getValue();
        divisionCmb.setItems(DBFirstLvlDivision.filterFirstLvlDiv(selectedCountry));
    }

    public void SaveAct(ActionEvent actionEvent) {
        String name = customerNameTxt.getText();
        String address = addressTxt.getText();
        String country = countryCmb.getValue();
        String division = divisionCmb.getValue();
        String postal = zipTxt.getText();
        String phone = phoneTxt.getText();

        System.out.println(name);

        try{
            catchError = getValidation(name,
                    address,
                    country,
                    division,
                    postal,
                    phone,
                    catchError);
            if (catchError.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Product Addition Warning");
                alert.setHeaderText("The product was NOT added!");
                alert.setContentText(catchError);
                alert.showAndWait();
                catchError = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    public void ExitBtn(ActionEvent actionEvent) {
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

    public void GoBackAct(ActionEvent actionEvent) throws IOException {
        Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerSelection.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartsStage.setScene(addPartsScene);
        addPartsStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryCmb.setItems(DBCountries.getAllCountries());
    }

    public static String getValidation(String name, String address, String country, String division, String phone, String postal, String ProductError){
        if (name == null) {
            ProductError = ProductError + " Name field required. ";
        }
        if (address == null) {
            ProductError = ProductError + " Address field required. ";
        }
        if(country == null) {
            ProductError = ProductError + " Country field required. ";
        }
        if (division == null) {
            ProductError = ProductError + " Division field required. ";
        }
        if (phone == null) {
            ProductError = ProductError + " Phone number required. ";
        }
        if (postal == null) {
            ProductError = ProductError + " Postal code required. ";
        }
        return ProductError;
    }

}

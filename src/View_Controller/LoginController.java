package View_Controller;

import DBAccess.DBUser;
import LocaleFiles.LocaleInfo;
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
import java.time.ZoneId;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    static int selectedUserIndex = 0;

    @FXML
    private javafx.scene.control.Button exitBtn;

    @FXML
    private javafx.scene.control.Button signinBtn;

    @FXML
    private Label unameLbl;
    @FXML
    private Label psswrdLbl;

    @FXML
    private TextField txtuname;

    @FXML
    private PasswordField txtpass;

    @FXML
    private Label zoneLbl;
    @FXML
    private Label ZONEIDLBL;
    ResourceBundle rb = ResourceBundle.getBundle("LocaleFiles/Nat", LocaleInfo.getLocale());

    /**This is the initialize method.
     it loads the all the pertinent information to the page.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
        zoneLbl.setText(zone.getId());
        unameLbl.setText(rb.getString("Login.username"));
        psswrdLbl.setText(rb.getString("Login.password"));
        exitBtn.setText(rb.getString("Login.exit"));
        signinBtn.setText(rb.getString("Login.signin"));
        ZONEIDLBL.setText(rb.getString("Login.zoneID"));

    }
    /**This is the the Sign in method.
     When the sign in button is clicked, the text field information is cross referenced to validate the user to enter the scheduling app.*/
    @FXML
    public void SigninAct(ActionEvent actionEvent) throws IOException, Exception {

        String uname = txtuname.getText();
        String pass = txtpass.getText();
        boolean enter = DBUser.loginUser(pass, uname);
        selectedUserIndex = DBUser.getUserID(uname);


        if (uname.equals("") && pass.equals("")) {
            JOptionPane.showMessageDialog(null, rb.getString("Login.Alert.BlankUserID"));
        }
        if (enter == true) {
            Database.Log.loginAttempts(uname,enter);

            Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerSelection.fxml"));
            Scene addPartsScene = new Scene(addPartsParent);
            Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            addPartsStage.setScene(addPartsScene);
            addPartsStage.show();
        }
        if (enter == false) {
            Database.Log.loginAttempts(uname,enter);
            JOptionPane.showMessageDialog(null, rb.getString("Login.Alert.FailedLogin"));
            txtuname.setText("");
            txtpass.setText("");
        }


    }


    /**This is the Exit method.
     When the Exit button is clicked, the application closes.*/
    @FXML
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
    ZoneId zone = ZoneId.systemDefault();




}

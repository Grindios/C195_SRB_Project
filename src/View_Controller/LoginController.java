package View_Controller;

import DBAccess.DBUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {



    @FXML
    private Button ExitBtn;

    @FXML
    private Button SigninBtn;


    @FXML
    private TextField txtuname;

    @FXML
    private PasswordField txtpass;

    @FXML
    public void SigninAct(ActionEvent actionEvent) throws IOException, Exception {

        String uname = txtuname.getText();
        String pass = txtpass.getText();
        boolean enter = DBUser.getUserId(pass, uname);




        if (uname.equals("") && pass.equals("")) {
            JOptionPane.showMessageDialog(null, "User ID Password cannot be blank.");
        }
        if (enter == true) {
            Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerSelection.fxml"));
            Scene addPartsScene = new Scene(addPartsParent);
            Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            addPartsStage.setScene(addPartsScene);
            addPartsStage.show();
        }
        if (enter == false) {
            JOptionPane.showMessageDialog(null, "Login Failed");
            txtuname.setText("");
            txtpass.setText("");
        }


    }



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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");

    }



}

package View_Controller;

import DBAccess.DBUsers;
import Database.DBConnection;
import Model.Appointment;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoginController implements Initializable {
    Logger userLog = Logger.getLogger("userlog.txt");

    @FXML
    private Button ExitBtn;

    @FXML
    private Button SigninBtn;

    @FXML
    private TextField userIDTxt;

    @FXML
    private TextField passwordTxt;

    @FXML
    public void SigninAct(ActionEvent actionEvent) throws IOException, Exception {
        String username = userIDTxt.getText();
        String password = passwordTxt.getText();
        boolean validUser = DBUsers.login(username, password);
        if(validUser) {
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AppointmentCalendar.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("There was a problem with your user ID or password.");
            alert.setContentText("Please check to see if you entered the correct login information.");
            alert.showAndWait();
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
    public static User loggedUser = new User();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
        Locale locale = Locale.getDefault();
        resourceBundle = ResourceBundle.getBundle("language/login", locale);
        usernameLabel.setText(resourceBundle.getString("username"));
        passwordLabel.setText(resourceBundle.getString("password"));
        loginButton.setText(resourceBundle.getString("login"));
        mainMessage.setText(resourceBundle.getString("message"));
        languageMessage.setText(resourceBundle.getString("language"));
        errorHeader = resourceBundle.getString("errorheader");
        errorTitle = resourceBundle.getString("errortitle");
        errorText = resourceBundle.getString("errortext");
    }



}

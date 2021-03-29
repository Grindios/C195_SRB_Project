package View_Controller;

import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        String userId = userIDTxt.getText();
        String password = passwordTxt.getText();
        LoggedUser.setUserName(userId);
        LoggedUser.setPassword(password);

        FileHandler userLogFH = new FileHandler("userlog.txt", true);
        SimpleFormatter sf = new SimpleFormatter();
        userLogFH.setFormatter(sf);
        userLog.addHandler(userLogFH);
        userLog.setLevel(Level.INFO);
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
    public static User LoggedUser = new User();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
    }



}

package main;



import Database.DBConnection;
import LocaleFiles.LocaleInfo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    ResourceBundle rb = ResourceBundle.getBundle("LocaleFiles/Nat", LocaleInfo.getLocale());
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Login.fxml"));
        primaryStage.setTitle(rb.getString("Main.title"));
        primaryStage.setScene(new Scene(root, 400, 250));
        primaryStage.show();
    }


    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();

    }
}

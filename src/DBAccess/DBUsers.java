package DBAccess;

import Database.DBConnection;
import Database.Log;
import Model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBUsers {
    private static User currentUser;

    public static User getCurrentUser(){
        return currentUser;
    }

    public static Boolean login(String username, String password) {
        try {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT * FROM user WHERE userName ='" + username + "'AND password='" + password+ "'";
            ResultSet results = statement.executeQuery(query);
            if (results.next()) {
                currentUser = new User();
                currentUser.setUserName(results.getString("userName"));
                statement.close();
                Log.log(username, true);
                return true;
            }else {
                Log.log(username, false);
                return false;
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }


}

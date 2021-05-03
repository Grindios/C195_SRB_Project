package DBAccess;

import Database.DBConnection;
import View_Controller.AddCustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBCountries {
    private static ObservableList<String> oblist = FXCollections.observableArrayList();
    public static ObservableList<String> getAllCountries() {
        try {
            Connection con = DBConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM countries");

            while (rs.next()){
                oblist.add(rs.getString("Country"));
            }
        }catch (SQLException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oblist;
    }
}

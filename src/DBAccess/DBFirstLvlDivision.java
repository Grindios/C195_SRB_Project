package DBAccess;

import Database.DBConnection;
import View_Controller.AddCustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBFirstLvlDivision {
    private static ObservableList<String> oblist = FXCollections.observableArrayList();
    public static ObservableList<String> filterFirstLvlDiv(String country) {



        try {

            if (country.contains("Canada")) {
                oblist.clear();
                Connection con = DBConnection.getConnection();

                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM first_level_divisions where Country_ID = 38;");

                while (rs.next()){
                    oblist.add(rs.getString("Division"));
                }
                return oblist;

            }
            if (country.contains("United Kingdom")) {
                oblist.clear();
                Connection con = DBConnection.getConnection();

                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM first_level_divisions where Country_ID = 230;");

                while (rs.next()){
                    oblist.add(rs.getString("Division"));
                }
                return oblist;
            }
            if (country.contains("United States")) {
                oblist.clear();
                Connection con = DBConnection.getConnection();

                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM first_level_divisions where Country_ID = 1;");

                while (rs.next()){
                    oblist.add(rs.getString("Division"));
                }
                return oblist;
            }

        }catch (SQLException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oblist;
    }


}

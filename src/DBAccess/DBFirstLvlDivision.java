package DBAccess;

import Database.DBConnection;
import View_Controller.AddCustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM first_level_divisions where Country_ID = 3;");

                while (rs.next()){
                    oblist.add(rs.getString("Division"));
                }
                return oblist;

            }
            if (country.contains("UK")) {
                oblist.clear();
                Connection con = DBConnection.getConnection();

                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM first_level_divisions where Country_ID = 2;");

                while (rs.next()){
                    oblist.add(rs.getString("Division"));
                }
                return oblist;
            }
            if (country.contains("U.S")) {
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

    public static String getDivisionName(String Division_ID) {
        String DivisionName = " ";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from first_level_divisions where Division_ID = ?;");
            pst.setInt(1, Integer.parseInt(Division_ID));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                DivisionName = rs.getString("Division");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return DivisionName;
    }


}

package DBAccess;

import Database.DBConnection;
import Model.Customer;
import View_Controller.CustomerSelectionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBCustomers {




    private static ObservableList<Customer> oblist = FXCollections.observableArrayList();
    public static ObservableList<Customer> getCustomers() {
        oblist.clear();
        try {
            Connection con = DBConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM customers");


            while (rs.next()){
                oblist.add(new Customer(rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Phone")));
            }
        }catch (SQLException ex) {
            Logger.getLogger(CustomerSelectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oblist;
    }


    public static void setCustomers(String name, String address, String postal, String phone, String division) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;
            String div_id;
            div_id = getDivisionID(division);


            pst = con.prepareStatement("INSERT INTO customers(Customer_Name,Address,Postal_Code,Phone, Division_ID) VALUES (?,?,?,?,?)");
            pst.setString(1, name);
            pst.setString(2, address);
            pst.setString(3, postal);
            pst.setString(4, phone);
            pst.setInt(5, Integer.parseInt(div_id));
            pst.executeUpdate();

        }catch (SQLException ex) {
            Logger.getLogger(CustomerSelectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getDivisionID(String division) {

        int div_id;
        String id_String = " ";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select Division_ID from first_level_divisions WHERE Division = ? AND Division_ID <= 3918;");
            pst.setString(1, division);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                div_id = rs.getInt("Division_ID");
                id_String = Integer.toString(div_id);
            }


        } catch (SQLException ex) {
            Logger.getLogger(CustomerSelectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id_String;
    }


}

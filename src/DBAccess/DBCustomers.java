package DBAccess;

import Database.DBConnection;
import Model.Customer;
import View_Controller.CustomerSelectionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBCustomers {

    private static ObservableList<Customer> oblist = FXCollections.observableArrayList();
    public static ObservableList<Customer> getCustomers() {

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
    public static void setCustomers(String name, String address, String country, String Division, String postal, String phone) {

    }


}

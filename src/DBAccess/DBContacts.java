package DBAccess;

import Database.DBConnection;
import View_Controller.AddCustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContacts {
    private static ObservableList<String> oblist = FXCollections.observableArrayList();
    public static ObservableList<String> getAllcontacts() {
        try {
            Connection con = DBConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM contacts");

            while (rs.next()){
                oblist.add(rs.getString("Contact_Name"));
            }
        }catch (SQLException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oblist;
    }
    public static int getContactID(String name) {
        int id = 0;


            String customerName = " ";

            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement pst;

                pst = con.prepareStatement(
                        "select * from contacts where Contact_Name = ?;");
                pst.setString(1, name);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    id = rs.getInt("Contact_ID");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return id;

    }
}

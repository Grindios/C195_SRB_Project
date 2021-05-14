package DBAccess;


import Database.DBConnection;
import Model.Appointment;
import Model.Customer;
import View_Controller.CustomerSelectionController;
import View_Controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBAppointment {
    private static ObservableList<Appointment> oblist = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAppointments() {
        oblist.clear();

        try {
            Connection con = DBConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM appointments");

            while (rs.next()) {
                oblist.add(new Appointment(rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        rs.getString("Start"),
                        rs.getString("end"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return oblist;
    }

    public static void setAppointment(String title, String description, String location, String contactName, String type, LocalDateTime startDate, LocalDateTime endDate, String cusotmerName, int user_ID) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;
            int customer_ID = DBCustomers.getCustomerID(cusotmerName);
            int contact_ID = DBContacts.getContactID(contactName);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd hh:mm:ss");
            String startDateStr = dtf.format(startDate);
            String endDateStr = dtf.format(endDate);



            pst = con.prepareStatement("INSERT INTO appointments(Title,Description,Location,Type,Start,End,Customer_ID,User_ID,Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)");
            pst.setString(1, title);
            pst.setString(2, description);
            pst.setString(3, location);
            pst.setString(4, type);
            pst.setString(5, startDateStr);
            pst.setString(6, endDateStr);
            pst.setInt(7, customer_ID);
            pst.setInt(8, user_ID);
            pst.setInt(9, contact_ID);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CustomerSelectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

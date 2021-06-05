package DBAccess;

import Database.DBConnection;
import Model.Customer;
import View_Controller.AddCustomerController;
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


            while (rs.next()) {
                oblist.add(new Customer(rs.getString("Customer_Name"),
                                        rs.getString("Address"),
                                        rs.getString("Phone"),
                                        rs.getInt("Customer_ID"),
                                        rs.getInt("Division_ID"),
                                        rs.getString("Postal_Code"),
                                        rs.getString("Phone")));
            }
        } catch (SQLException ex) {
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

        } catch (SQLException ex) {
            Logger.getLogger(CustomerSelectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void modifyCustomers(String name, String address, String postal, String phone, String division, int customer_ID) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;
            String div_id;
            div_id = getDivisionID(division);


            pst = con.prepareStatement("UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID =? ");
            pst.setString(1, name);
            pst.setString(2, address);
            pst.setString(3, postal);
            pst.setString(4, phone);
            pst.setInt(5, Integer.parseInt(div_id));
            pst.setInt(6, customer_ID);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CustomerSelectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void deleteCustomer(String customer_ID) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;
            int deleteMe = Integer.parseInt(customer_ID);


            pst = con.prepareStatement("DELETE FROM customers WHERE Customer_ID = ?");
            pst.setInt(1, deleteMe);
            pst.executeUpdate();

        } catch (SQLException ex) {
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



    public static String doesAppointmentExist(String customer_ID) {
        int apt_id;
        String apt_idString= " ";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from appointments where Customer_ID = ?;");
            pst.setString(1, customer_ID);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                apt_id = rs.getInt("Appointment_ID");
                apt_idString = Integer.toString(apt_id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return apt_idString;
    }






    // search for load customer data

    public static String getCustomerName(String customer_ID) {
        String customerName = " ";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from customers where Customer_ID = ?;");
            pst.setInt(1, Integer.parseInt(customer_ID));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                customerName = rs.getString("Customer_Name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerName;
    }
    public static String getCustomerAddress(String Customer_ID) {
        String customerAddress = " ";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from customers where Customer_ID = ?;");
            pst.setInt(1, Integer.parseInt(Customer_ID));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                customerAddress = rs.getString("Address");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerAddress;
    }
    public static String getCountry(String Customer_ID) {
        String CountryName = " ";
        int divID = 0;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from customers where Customer_ID = ?;");
            pst.setInt(1, Integer.parseInt(Customer_ID));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                divID = rs.getInt("Division_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (divID >=1 && divID <= 54) {
            CountryName = "United States";
        }
        if (divID >= 663 && divID <= 675) {
            CountryName = "Canada";
        }
        if (divID >=101 && divID <= 104) {
            CountryName = "United Kingdom";
        }
        return CountryName;
            }
    public static String getDivision(String Customer_ID) {
        int Division_ID =0 ;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from customers where Customer_ID = ?;");
            pst.setInt(1, Integer.parseInt(Customer_ID));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Division_ID = rs.getInt("Division_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String divName = DBFirstLvlDivision.getDivisionName(Integer.toString(Division_ID));
        return divName;
    }
    public static String getPostalCode(String Customer_ID) {
        String customerPostal = " ";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from customers where Customer_ID = ?;");
            pst.setInt(1, Integer.parseInt(Customer_ID));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                customerPostal = rs.getString("Postal_Code");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerPostal;
    }
    public static String getPhone(String Customer_ID) {
        String customerPhone = " ";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from customers where Customer_ID = ?;");
            pst.setInt(1, Integer.parseInt(Customer_ID));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                customerPhone = rs.getString("Phone");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerPhone;
    }
    public static int getCustomerID(String name) {
        int id = 0;

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from customers where Customer_Name = ?;");
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                id = rs.getInt("Customer_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }
















    //stand alone for test

    private static ObservableList<String> customerList = FXCollections.observableArrayList();
    public static ObservableList<String> getAllCustomers() {
        customerList.clear();
        try {
            Connection con = DBConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM customers");

            while (rs.next()){
                customerList.add(rs.getString("Customer_Name"));
            }
        }catch (SQLException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customerList;
    }


}

package DBAccess;


import Database.DBConnection;
import Model.Appointment;
import View_Controller.CustomerSelectionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBAppointment {
    private static ObservableList<Appointment> weekOblist = FXCollections.observableArrayList();
    private static ObservableList<Appointment> monthOblist = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allOblist = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAllAppointments() {
        allOblist.clear();
        weekOblist.clear();
        monthOblist.clear();

        String Contact_ID;
        String Contact_Name;

        try {
            Connection con = DBConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM appointments;");

            while (rs.next()) {

                allOblist.add(new Appointment(rs.getInt("Appointment_ID"),
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
        return allOblist;
    }

    public static ObservableList<Appointment> getAppointmentsThisWeek() {
        allOblist.clear();
        weekOblist.clear();
        monthOblist.clear();

        try {
            Connection con = DBConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT *FROM appointments where WEEK(Start) = WEEK(curdate());");

            while (rs.next()) {
                weekOblist.add(new Appointment(rs.getInt("Appointment_ID"),
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
        return weekOblist;
    }

    public static ObservableList<Appointment> getAppointmentsThisMonth() {
        allOblist.clear();
        weekOblist.clear();
        monthOblist.clear();

        String Contact_ID;
        String Contact_Name;

        try {
            Connection con = DBConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT *FROM appointments where MONTH(Start) = MONTH(curdate()) && YEAR(Start) = year(curdate());");

            while (rs.next()) {
                monthOblist.add(new Appointment(rs.getInt("Appointment_ID"),
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
        return monthOblist;
    }

    public static void setAppointment(String title, String description, String location, String contactName, String type, LocalDateTime startDate, LocalDateTime endDate, String cusotmerName, int user_ID) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;
            int customer_ID = DBCustomers.getCustomerID(cusotmerName);
            int contact_ID = DBContacts.getContactID(contactName);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
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
    public static void editAppointment(String title, String description, String location, String contactName, String type, LocalDateTime startDate, LocalDateTime endDate, String cusotmerName, int user_ID, int apt_ID) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;
            int customer_ID = DBCustomers.getCustomerID(cusotmerName);
            int contact_ID = DBContacts.getContactID(contactName);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
            String startDateStr = dtf.format(startDate);
            String endDateStr = dtf.format(endDate);


            pst = con.prepareStatement("UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID =?");
            pst.setString(1, title);
            pst.setString(2, description);
            pst.setString(3, location);
            pst.setString(4, type);
            pst.setString(5, startDateStr);
            pst.setString(6, endDateStr);
            pst.setInt(7, customer_ID);
            pst.setInt(8, user_ID);
            pst.setInt(9, contact_ID);
            pst.setInt(10, apt_ID);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CustomerSelectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteAppointment(int appointment_ID) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;


            pst = con.prepareStatement("DELETE FROM appointments WHERE Appointment_ID = ?");
            pst.setInt(1, appointment_ID);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CustomerSelectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    // Looking up id
    public static String getCustomerCmb (int id) {
        int customer_ID = 0;

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from appointments where Appointment_ID = ?;");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                customer_ID = rs.getInt("Customer_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String customer_Name = DBCustomers.getCustomerName(Integer.toString(customer_ID));
        return customer_Name;
    }
    public static String getContactCmb(int id) {
        int contact_ID = 0;

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from appointments where Appointment_ID = ?;");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                contact_ID = rs.getInt("Contact_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String contact_name = DBContacts.getContactName(contact_ID);
        return contact_name;
    }

    public static int getStartHour(int id) {
        int Hour = 0;
        Date entireDate = null;
        String stringDateTime = null;

        int fake_Hour = 12;


        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from appointments where Appointment_ID = ?;");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                stringDateTime = rs.getString("Start");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        try {
            System.out.println(sdf.parse(stringDateTime));
            entireDate = sdf.parse(stringDateTime);
            System.out.println(entireDate);
            Hour = entireDate.getHours();
            System.out.println(Hour);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Hour;
    }

    public static int getStartMinute(int id) {
        int Minute = 0;
        Date entireDate = null;
        String stringDateTime = null;

        int fake_Hour = 12;


        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from appointments where Appointment_ID = ?;");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                stringDateTime = rs.getString("Start");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        try {
            System.out.println(sdf.parse(stringDateTime));
            entireDate = sdf.parse(stringDateTime);
            System.out.println(entireDate);
            Minute = entireDate.getMinutes();
            System.out.println(Minute);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Minute;

    }

    public static int getEndHour(int id) {
        int Hour = 0;
        Date entireDate = null;
        String stringDateTime = null;



        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from appointments where Appointment_ID = ?;");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                stringDateTime = rs.getString("End");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        try {
            System.out.println(sdf.parse(stringDateTime));
            entireDate = sdf.parse(stringDateTime);
            System.out.println(entireDate);
            Hour = entireDate.getHours();
            System.out.println(Hour);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Hour;
    }

    public static int getEndMinute(int id) {
        int Minute = 0;
        Date entireDate = null;
        String stringDateTime = null;

        int fake_Hour = 12;


        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from appointments where Appointment_ID = ?;");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                stringDateTime = rs.getString("End");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        try {
            System.out.println(sdf.parse(stringDateTime));
            entireDate = sdf.parse(stringDateTime);
            System.out.println(entireDate);
            Minute = entireDate.getMinutes();
            System.out.println(Minute);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Minute;

    }

    public static String getTitleTxt(int id) {
        String titleText = "";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from appointments where Appointment_ID = ?;");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                titleText = rs.getString("Title");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return titleText;
    }

    public static String getLocationTxt(int id) {
        String locationText = "";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from appointments where Appointment_ID = ?;");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                locationText = rs.getString("Location");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return locationText;
    }

    public static String getTypeTxt(int id) {
        String typeText = "";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from appointments where Appointment_ID = ?;");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                typeText = rs.getString("Type");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return typeText;
    }

    public static String getDescriptionTxt(int id) {
        String descriptionText = "";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from appointments where Appointment_ID = ?;");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                descriptionText = rs.getString("Location");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return descriptionText;
    }

    public static LocalDate getStartDate(int id) {


        LocalDate simpDate = null;


        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from appointments where Appointment_ID = ?;");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                //ts = rs.getTimestamp("Start");
                simpDate = rs.getObject("Start", LocalDate.class);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println(simpDate);

        return simpDate;
    }

    public static LocalDate getEndDate(int id) {

        LocalDate simpDate = null;


        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from appointments where Appointment_ID = ?;");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                //ts = rs.getTimestamp("Start");
                simpDate = rs.getObject("end", LocalDate.class);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println(simpDate);

        return simpDate;
    }

    //aggregation of date

}


package Model;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    String title, description, location, type, start, end;
    int aptID, customerID, userID, contactID;

    public Appointment (int aptID,String title, String description, String location, String type, String start, String end, int customerID, int userID, int contactID) {
        this.aptID = aptID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getAptID() {
        return aptID;
    }

    public void setAptID(int aptID) {
        this.aptID = aptID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }


    // Time Aggregation and validation

    public static LocalDateTime TimeAggregation(LocalDate date, int hour, int minute) {

        LocalDateTime inDate = LocalDateTime.of(date.getYear(),date.getMonth(),date.getDayOfMonth(),hour,minute);


        return inDate;
    }

    public static Boolean validateTimeRange(LocalDateTime start, LocalDateTime end ) {
        Boolean valDate = false;

        if (start.getYear() > end.getYear()) {
            JOptionPane.showMessageDialog(null, "Start year cannot be greater than end year.");
            valDate = false;
        }
        if (start.getYear() <= end.getYear()) {
            if (start.getYear() == end.getYear()){
                if (start.getDayOfYear() > end.getDayOfYear()) {
                    JOptionPane.showMessageDialog(null, "Start Date cannot be ahead of End Date.");
                    valDate = false;
                }
                if (start.getDayOfYear() <= end.getDayOfYear()){
                    if (start.getDayOfYear() == end.getDayOfYear()) {
                        if (start.getHour() > end.getHour()) {
                            JOptionPane.showMessageDialog(null, "Start Time cannot be ahead of End Time.");
                            valDate = false;
                        }
                        if (start.getHour() <= end.getHour()) {
                            if (start.getMinute() > end.getMinute() && start.getMinute() == end.getMinute()) {
                                JOptionPane.showMessageDialog(null, "Start Minute cannot be ahead of or equal End Minute.");
                                valDate = false;
                            }
                            if (start.getHour() == end.getHour() && start.getMinute() == end.getMinute()) {
                                JOptionPane.showMessageDialog(null, "Time/Date cannot be the same.");
                            }

                        }


                    }
                    if (start.getDayOfYear() < end.getDayOfYear()) {
                        valDate = true;
                    }
                }
            }

        }
        return valDate;
    }
    public static Boolean validateBusinessHours(int startHour, int startMinute, int endHour, int endMin) {
        boolean valTime = false;
        if (startHour < 8 || startHour > 22){
            JOptionPane.showMessageDialog(null, "Start time is not within business Hours- 8: 00 AM - 10:00 PM.");
            valTime = false;
        }
        if ( startHour == 22) {
            if ( startMinute > 0) {
                JOptionPane.showMessageDialog(null, "Start time is not within business Hours- 8: 00 AM - 10:00 PM..");
                valTime = false;
            }
        }
        if (endHour < 8 || endHour > 22){
            JOptionPane.showMessageDialog(null, "End time is not within business Hours- 8: 00 AM - 10:00 PM.");
            valTime = false;
        }
        if ( endHour == 22) {
            if ( endMin > 0) {
                JOptionPane.showMessageDialog(null, "End time is not within business Hours- 8: 00 AM - 10:00 PM..");
                valTime = false;
            }
        }
        else {
            valTime = true;
        }

        return valTime;
    }
    public static String validateTextFields(String title, String description, String location, String contact, String type, String cusotmer, String error) {


        if (title.isEmpty()) {
            error = error + "Title field required. ";
        }
        if (description.isEmpty()) {
            error = error + "\nDescription field required. ";
        }
        if (location.isEmpty()) {
            error = error + "\nLocation field required. ";
        }
        if (contact == null ) {
            error = error + "\nContact field required.";
        }
        if (type.isEmpty()) {
            error = error + "\ncustomer field required. ";
        }
        if (cusotmer == null) {
            error = error + "\ncustomer field required.";
        }
    return error;

    }
}

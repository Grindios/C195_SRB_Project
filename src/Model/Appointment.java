package Model;

import DBAccess.DBContacts;
import LocaleFiles.LocaleInfo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class Appointment {
    String title, description, location, type;
    int aptID, customerID, userID, contact_id;
    ZonedDateTime start, end;

    public Appointment (int aptID,String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end, int customerID, int userID, int contact_id) {
        this.aptID = aptID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contact_id = contact_id;

    }

    public String getContact_id() {
        String contactName = "";
        contactName = DBContacts.getContactName(contact_id);
        return contactName;
    }
    ZoneId toTimeZone = ZoneId.systemDefault();
    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
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

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public LocalDateTime getStart() {
        LocalDateTime startLDT = start.toLocalDateTime();
        return startLDT;
    }

    public LocalDateTime getEnd() {
        LocalDateTime endLDT = end.toLocalDateTime();
        return endLDT;
    }

    public ZoneId getToTimeZone() {
        return toTimeZone;
    }

    public void setToTimeZone(ZoneId toTimeZone) {
        this.toTimeZone = toTimeZone;
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



    public static String validateAppointmentFields(String title, String description, String location, String contact, String type, String cusotmer, String error, ZonedDateTime start, ZonedDateTime end, int startHour, int startMinute, int endHour, int endMin) {
        ResourceBundle rb = ResourceBundle.getBundle("LocaleFiles/Nat", LocaleInfo.getLocale());
        // validating Text fields

        if (title.isEmpty()) {
            error = error + rb.getString("Appointment.Error.Title");
        }
        if (description.isEmpty()) {
            error = error + rb.getString("Appointment.Error.Description");
        }
        if (location.isEmpty()) {
            error = error + rb.getString("Appointment.Error.Location");
        }
        if (contact == null ) {
            error = error + rb.getString("Appointment.Error.Contact");
        }
        if (type.isEmpty()) {
            error = error + rb.getString("Appointment.Error.Type");
        }
        if (cusotmer == null) {
            error = error + rb.getString("Appointment.Error.Customer");
        }
        //validating time ranges
        if (startHour < 8 || startHour > 22){

            error = error + rb.getString("Appointment.Error.TimeRange1");
        }
        if ( startHour == 22) {
            if ( startMinute > 0) {

                error = error + rb.getString("Appointment.Error.TimeRange2");
            }
        }
        if (endHour < 8 || endHour > 22){
            error = error + rb.getString("Appointment.Error.TimeRange3");
        }
        if ( endHour == 22) {
            if ( endMin > 0) {
                error = error + rb.getString("Appointment.Error.TimeRange4");
            }
        }

        //Validating time


        if (start.getYear() > end.getYear()) {
            error = error + rb.getString("Appointment.Error.Time1");

        }
        if (start.getYear() <= end.getYear()) {
            if (start.getYear() == end.getYear()){
                if (start.getDayOfYear() > end.getDayOfYear()) {
                    error = error + rb.getString("Appointment.Error.Time2");
                }
                if (start.getDayOfYear() <= end.getDayOfYear()){
                    if (start.getDayOfYear() == end.getDayOfYear()) {
                        if (start.getHour() > end.getHour()) {
                            error = error + rb.getString("Appointment.Error.Time3");
                        }
                        if (start.getHour() <= end.getHour()) {
                            if (start.getMinute() > end.getMinute() && start.getMinute() == end.getMinute()) {
                                error = error + rb.getString("Appointment.Error.Time4");
                            }
                            if (start.getHour() == end.getHour() && start.getMinute() == end.getMinute()) {
                                error = error + rb.getString("Appointment.Error.Time5");
                            }

                        }


                    }
                    if (start.getDayOfYear() < end.getDayOfYear()) {

                    }
                }
            }

        }

    return error;

    }
}

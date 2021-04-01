package Model;

import DBAccess.DBAppointment;
import Exceptions.AppointmentException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Appointment {
    private IntegerProperty appointmentId = new SimpleIntegerProperty();
    private IntegerProperty customerId = new SimpleIntegerProperty();
    private IntegerProperty userId = new SimpleIntegerProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty location = new SimpleStringProperty();
    private StringProperty contact = new SimpleStringProperty();
    private StringProperty type = new SimpleStringProperty();
    private StringProperty url = new SimpleStringProperty();
    private ZonedDateTime start;
    private ZonedDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    private Customer customer = new Customer();


    public Appointment() {

    }
    public IntegerProperty getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(IntegerProperty appointmentId) {
        this.appointmentId = appointmentId;
    }

    public IntegerProperty getCustomerId() {
        return customerId;
    }

    public void setCustomerId(IntegerProperty customerId) {
        this.customerId = customerId;
    }

    public IntegerProperty getUserId() {
        return userId;
    }

    public void setUserId(IntegerProperty userId) {
        this.userId = userId;
    }

    public StringProperty getTitle() {
        return title;
    }

    public void setTitle(StringProperty title) {
        this.title = title;
    }

    public StringProperty getDescription() {
        return description;
    }

    public void setDescription(StringProperty description) {
        this.description = description;
    }

    public StringProperty getLocation() {
        return location;
    }

    public void setLocation(StringProperty location) {
        this.location = location;
    }

    public StringProperty getContact() {
        return contact;
    }

    public void setContact(StringProperty contact) {
        this.contact = contact;
    }

    public StringProperty getType() {
        return type;
    }

    public void setType(StringProperty type) {
        this.type = type;
    }

    public StringProperty getUrl() {
        return url;
    }

    public void setUrl(StringProperty url) {
        this.url = url;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isValidIdInput() throws AppointmentException {
        if (this.customer == null) {
            throw new AppointmentException("There was no customer selected!");
        }
        if (this.title.get().equals("")) {
            throw new AppointmentException("You must enter a title!");
        }
        if (this.description.get().equals("")) {
            throw new AppointmentException("You must enter a description!");
        }
        if (this.location.get().equals("")) {
            throw new AppointmentException("You must enter a location!");
        }
        if (this.contact.get().equals("")) {
            throw new AppointmentException("You must enter a contact!");
        }
        if (this.type.get().equals("")) {
            throw new AppointmentException("You must enter a type!");
        }
        if (this.url.get().equals("")) {
            throw new AppointmentException("You must enter a url!");
        }
        isValidTime();
        return true;
    }

    public boolean isValidTime() throws AppointmentException {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate apptStartDate = this.start.toLocalDate();
        LocalTime apptStartTime = this.start.toLocalTime();
        LocalDate apptEndDate = this.end.toLocalDate();
        LocalTime apptEndTime = this.end.toLocalTime();
        int weekDay = apptStartDate.getDayOfWeek().getValue();

        if (!apptStartDate.isEqual(apptEndDate)) {
            throw new AppointmentException("An appoinment can only be a single day!");
        }
        if (weekDay == 6 || weekDay == 7) {
            throw new AppointmentException("An appointment can only be scheduled on weekdays!");
        }
        if (apptStartTime.isBefore(midnight.plusHours(8))) {
            throw new AppointmentException("An appointment cannot be scheduled before normal business hours!");
        }
        if (apptEndTime.isAfter(midnight.plusHours(17))) {
            throw new AppointmentException("An appointment cannot be scheduled after normal business hours!");
        }
        if (apptStartDate.isBefore(LocalDate.now()) || apptStartTime.isBefore(LocalTime.MIDNIGHT)) {
            throw new AppointmentException("An appointment cannot be scheduled in the past!");
        }
        return true;
    }

    public boolean isNotOverlapping() throws AppointmentException {
        ObservableList<Appointment> overlappingAppt = DBAppointment.getOverlappingAppts(this.start.toLocalDateTime(), this.end.toLocalDateTime());
        if (overlappingAppt.size() > 1) {
            throw new AppointmentException("An appointment cannot be scheduled at the same time as another appointment!");
        }
        return true;
    }


}

package Model;

import LocaleFiles.LocaleInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ResourceBundle;

public class Customer {
    public static String entryValidation;
    String customer,addy,phone,postal;
    int id,division;

    public Customer(String customer, String addy, String phone, int id, int division, String postal_code, String postal) {
        this.customer = customer;
        this.addy = addy;
        this.phone = phone;
        this.id = id;
        this.division = division;
        this.postal = postal;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAddy() {
        return addy;
    }

    public void setAddy(String addy) {
        this.addy = addy;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public static String entryValidation(String name, String address, String phone, String division, String postal,String error) {
        ResourceBundle rb = ResourceBundle.getBundle("LocaleFiles/Nat", LocaleInfo.getLocale());
        if (name.isEmpty()) {
            error = error + rb.getString("Model.Customer.valName");
        }
        if (address.isEmpty()) {
            error = error + rb.getString("Model.Customer.valaddress");
        }
        if (phone.isEmpty()) {
            error = error + rb.getString("Model.Customer.valphone");
        }
        if (division == null) {
            error = error + rb.getString("Model.Customer.valdivision");
        }
        if (postal.isEmpty()) {
            error = error + rb.getString("Model.Customer.valpostal");
        }

        return error;
    }

}

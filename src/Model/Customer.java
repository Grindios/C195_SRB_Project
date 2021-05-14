package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {
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


}

package View_Controller;

import DBAccess.DBContacts;
import Database.DBConnection;
import LocaleFiles.LocaleInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    @FXML
    private RadioButton byTypeRdbtn;
    @FXML
    private RadioButton byMonthRdbtn;
    @FXML
    private TextArea mainTxtArea;
    @FXML
    private Button customerAptBtn;
    @FXML
    private Button contactAptBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button aptlocationBtn;

    ResourceBundle rb = ResourceBundle.getBundle("LocaleFiles/Nat", LocaleInfo.getLocale());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    backBtn.setText(rb.getString("Report.backBtn"));
    customerAptBtn.setText(rb.getString("Report.customerAptBtn"));
    contactAptBtn.setText(rb.getString("Report.contactAptBtn"));
    aptlocationBtn.setText(rb.getString("Report.aptlocationBtn"));
    byTypeRdbtn.setText(rb.getString("Report.TypeRadioButton"));
    byMonthRdbtn.setText(rb.getString("Report.MonthRadioButton"));

    }


    public void BackAct(ActionEvent actionEvent) throws IOException {
        Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerSelection.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartsStage.setScene(addPartsScene);
        addPartsStage.show();
    }

    public void CustomerAptAct(ActionEvent actionEvent) {
        StringBuffer sbf = new StringBuffer("");
        if (byTypeRdbtn.isSelected()) {
            int end = 0;
            end = sbf.length();
            sbf.delete(0,end);
            int total ;
            String type = "";

            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement pst;

                pst = con.prepareStatement(
                        "select COUNT(Type) as Total, Type FROM appointments group by Type;");

                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    total = rs.getInt("Total");
                    type = rs.getString("Type");
                    sbf.append(rb.getString("Report.ContactAppointments.1") + Integer.toString(total) + rb.getString("Report.CustomerAppointments.2") + type + ".\n");

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            mainTxtArea.setText(String.valueOf(sbf));

        }
        if (byMonthRdbtn.isSelected()) {
            int end = 0;
            end = sbf.length();
            sbf.delete(0,end);
            int total ;
            String Month = "";

            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement pst;

                pst = con.prepareStatement("SELECT COUNT(monthname(Start)) as Total, monthname(Start) FROM appointments GROUP BY monthname(Start);");

                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    total = rs.getInt("Total");
                    Month = rs.getString("monthname(Start)");
                    sbf.append(rb.getString("Report.CustomerAppointments.1") + " " +Integer.toString(total) + rb.getString("Report.CustomerAppointments.3")+ " " + Month + ".\n");

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            mainTxtArea.setText(String.valueOf(sbf));

        }
    }

    public void typeSelectionAct(ActionEvent actionEvent) {
        byMonthRdbtn.setSelected(false);
    }

    public void monthSelectionAct(ActionEvent actionEvent) {
        byTypeRdbtn.setSelected(false);
    }
    private static ObservableList<String> oblist = FXCollections.observableArrayList();
    public void contactApointmentsAct(ActionEvent actionEvent) {
        oblist = DBContacts.getAllcontacts();
        StringBuffer sbf = new StringBuffer("");
        int aptID,customerID;
        String title,type,description,start,end;

        int i = 0;

        for (i = 0; i < oblist.size(); i++) {
            sbf.append("_______________________________________________________________________");
            sbf.append("\n"+oblist.get(i) + " " /*+ rb.getString("Report.ContactAppointments.1" + "\n")*/ );

            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement pst;

                pst = con.prepareStatement("SELECT * FROM appointments WHERE Contact_ID = ?;");
                pst.setInt(1, i+1);
                System.out.println(i);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {

                    aptID = rs.getInt("Appointment_ID");
                    title = rs.getString("Title");
                    description = rs.getString("Description");
                    type = rs.getString("Type");
                    start = rs.getString("Start");
                    end = rs.getString("End");
                    customerID = rs.getInt("Customer_ID");

                    sbf.append("\n______________\n");
                    sbf.append(rb.getString("Report.ContactAppointments.2") +" "+ aptID);
                    sbf.append("\n"+rb.getString("Report.ContactAppointments.3")+" "+ title);
                    sbf.append("\n" +rb.getString("Report.ContactAppointments.4")+" "+ description);
                    sbf.append("\n"+rb.getString("Report.ContactAppointments.5")+" " +type);
                    sbf.append("\n"+rb.getString("Report.ContactAppointments.6")+ " " + start);
                    sbf.append("\n" + rb.getString("Report.ContactAppointments.7") + " " + end);
                    sbf.append("\n"+ rb.getString("Report.ContactAppointments.8")+ " " +customerID+"\n");

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            mainTxtArea.setText(String.valueOf(sbf));


        }


    }


    public void aptLocationAct(ActionEvent actionEvent) {
        StringBuffer sbf = new StringBuffer("");

            int end = 0;
            end = sbf.length();
            sbf.delete(0,end);
            int total ;
            String location = "";

            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement pst;

                pst = con.prepareStatement(
                        "select COUNT(Location) as Total, Location FROM appointments group by Location;");

                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    total = rs.getInt("Total");
                    location = rs.getString("Location");
                    sbf.append(rb.getString("Report.AppointmentLocation.1")+ " " + Integer.toString(total) + rb.getString("Report.AppointmentLocation.2") + " " + location + ".\n");

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            mainTxtArea.setText(String.valueOf(sbf));




    }
}

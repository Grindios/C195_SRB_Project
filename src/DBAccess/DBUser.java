package DBAccess;

import Database.DBConnection;

import javax.swing.*;
import java.sql.*;

public class DBUser {
    private final static Connection DB_CONN = DBConnection.getConnection();




    public static boolean loginUser(String uname, String pass) {
        boolean enter = false;
        try {

            PreparedStatement pst = DB_CONN.prepareStatement("SELECT * FROM users WHERE user_Name=? and Password=?");
            ResultSet rs;
            pst.setString(1, uname);
            pst.setString(2, pass);

            rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Login Success!");
                enter = true;
            } else {
                enter = false;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return enter;
    }
    public static int getUserID(String userName) {
        int userID = 0;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst;

            pst = con.prepareStatement(
                    "select * from users where User_Name = ?;");
            pst.setString(1, userName);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                userID = rs.getInt("User_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userID;

    }

}

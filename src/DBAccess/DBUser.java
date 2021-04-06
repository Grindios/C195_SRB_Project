package DBAccess;

import Database.DBConnection;
import Database.Log;
import Model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBUser {
    private final static Connection conn = DBConnection.getConnection();



    public static User getUserId(int user_ID) {
        String getUIDSQL = "SELECT * FROM users WHERE user_ID =?";
        User user = new User();

        try {
            PreparedStatement stmt = conn.prepareStatement(getUIDSQL);
            stmt.setInt(1, user_ID);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                user.setUserId(rs.getInt("user_ID"));
                user.setUserName((rs.getString("userName")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}

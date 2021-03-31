package Database;


import java.sql.Connection;

public class DBConnection {
    private static final String protocol = "";
    private static final String vendorName = "";
    private static final String ipAddress = "";
    private static final String dbName = "";
    private static final String Password = "";

    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    private static final String MYSQLJBCDriver = "";

    private static final String username = "";
    private  static Connection conn = null;

    public static void startConnection() {

    }


}

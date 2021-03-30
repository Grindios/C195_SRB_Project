package DBAccess;

import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBCountries {
        public static ObservableList<Countries> getAllCountries() {
            ObservableList<Countries> clist = FXCollections.observableArrayList();

            try {
                String sql = "SELECT * from countries";

                PreparedStatement ps = Database.getConnection();

            }


            return clist;
        }

        public static void checkDataConversion(){
            System.out.println("CREATE DATE TEST");
            String sql = "select Create_Date from countries";
            try {

            }
        }


}

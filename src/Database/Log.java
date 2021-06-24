package Database;


import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Log {



    public static final String filename = "Login_Activity.txt";
    public Log(){};



    public static void loginAttempts(String uname, Boolean enter) throws IOException {
        LocalDate date = LocalDate.now();
        LocalDateTime  dateTime = LocalDateTime.now();

        try {
            FileWriter fw = new FileWriter(filename, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println("There was a  SUCCESSFUL log in attempt on " + date + " with the user name \" " + uname + " \" at " + dateTime.getHour() + ":" + dateTime.getMinute()+ "");
            pw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }



    }


}

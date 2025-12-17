import java.io.*;
import java.sql.*;
import java.util.Scanner;
import static java.lang.System.exit;
public class DBConnect {
    // Singleton instance is called mysqlConn
    private static Connection mysqlConn = null;

    static {
        String url = "jdbc:mysql://devweb2025.cis.strath.ac.uk:3306/fsb23151?useSSL=false&serverTimezone=UTC";
        String dbName = "fsb23151";
        String user = "fsb23151";
        String password = "aeKoh5angi3t";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            mysqlConn = DriverManager.getConnection(url, user, password);
            System.out.println("MySQL Db Connection is successful");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();}
    }
    // This is the method that you call to get the singleton object
    public static Connection getMysqlConnection()
    {return mysqlConn;}

}





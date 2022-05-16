
import java.sql.*;

public class Database {

    Connection con;

    public Database() {
        Connect();
    }

    public static Connection Connect() {

        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/ahs", "root", "");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return con;
    }
}

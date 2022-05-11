import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer {

    private String Uid;
    public int Amount;
    private String Password;
    private int LocalDistributor;

    public Consumer(String Uid,  String Password) {
        this.Uid = Uid;
        this.Password = Password;
    }

    Connection con;

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



    public boolean buy(int LDid, int amount) {


        return true;
    }

    public boolean login() {

        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT * FROM `users` WHERE `uid` =? AND `pass` =? AND `type`=?";

        try {
            ps = Consumer.Connect().prepareStatement(query);

            ps.setString(1, this.Uid);
            ps.setString(2, this.Password);
            ps.setString(3,"1");

            rs = ps.executeQuery();
            return true;
        }catch (SQLException ex) {
                return false;
            }


    }


}

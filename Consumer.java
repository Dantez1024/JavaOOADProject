
import java.sql.*;
public class Consumer {

    private String Uid;
    private int ID;
    public int Amount_bought;
    public String Name;
    private String Password;
   private int Wallet;


    static Database con;
    static LocalDistributor dist;



    public Consumer(String Uid,  String Password) {
        this.Uid = Uid;
        this.Password = Password;

        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;

        String query = "SELECT * FROM `users` WHERE `Uid` =?  ";

        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setString(1, this.Uid);

            rs = ps.executeQuery();
            if(rs.next()) {
                int id = rs.getInt("ID");

             //   System.out.println(id);

                String query2 = "SELECT * FROM `consumers` WHERE `Uid` =?  ";
                try {
                    ps2 = Database.Connect().prepareStatement(query2);

                    ps2.setInt(1, id);

                    rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        this.ID = rs2.getInt("CID");
                        this.Amount_bought = rs2.getInt("Amnt_bought");
                        this.Name = rs2.getString("CName");
                        this.Wallet = rs2.getInt("Wallet");

                      //  System.out.println("Welcome : "+this.Name);
                    }
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }else{
                this.Amount_bought = 0;

            }


        } catch (SQLException e) {
            e.printStackTrace();


        }
    }

    public String getName() {
        return this.Name;
    }
    public int getWalletBAlance() { return this.Wallet;}

    public boolean checkWallet() {
        if(this.Wallet > 0)
        {
            return true;
        }else {
            return false;
        }
    }








    public boolean buy(int  LDid, int amount) {


        con = new Database();
        double price;

        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT * FROM `LocalDistributors` WHERE `Uid` =? ";

        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setInt(1, LDid);

            rs = ps.executeQuery();
            if(rs.next()) {
            int lamt = rs.getInt("Stock_level");
            price = rs.getDouble("Price");
            double fprice = amount * price;

            if(this.Wallet >= fprice) {
                double newwallet = this.Wallet - fprice;
                int Lamount = lamt - amount;
                updateAmounts(LDid, Lamount);
                updateWallet(newwallet);
                System.out.println("Fuel Cost : "+fprice+"\n");
                System.out.println("Wallet Balance : "+newwallet+"\n");
            }else{
                System.out.println("Insufficient Wallet Balance\n");
                System.out.println("Fuel Cost : "+fprice);
                System.out.println("Wallet Balance : "+this.Wallet);
            }
            }


        }catch (SQLException e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());

            return false;
        }




        return true;
    }

    public void updateWallet(double bal) {
        con = new Database();
        PreparedStatement ps;
        ResultSet rs;

        String query = "update consumers set Wallet = ? where CID = ?";

        try {
            PreparedStatement preparedStmt = con.Connect().prepareStatement(query);
            preparedStmt.setDouble   (1, bal);
            preparedStmt.setInt(2, this.ID);


            // execute the java preparedstatement
            preparedStmt.executeUpdate();




        }
        catch (Exception e)
        {
            System.err.println("Error Updating Wallet!! ");
            System.err.println(e.getMessage());


        }
    }

    public boolean updateAmounts(int LDid, int amount) {
        con = new Database();
        PreparedStatement ps;
        ResultSet rs;

        String query = "update LocalDistributors set Stock_level = ? where Uid = ?";

        try {
        PreparedStatement preparedStmt = con.Connect().prepareStatement(query);
        preparedStmt.setInt   (1, amount);
        preparedStmt.setInt(2, LDid);


            // execute the java preparedstatement
            preparedStmt.executeUpdate();

            return true;
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());

            return false;
        }



    }




}

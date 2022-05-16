import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Importer {

    private String  Uid;
    private int ID;
    public int Stock_level;
    private int Wallet;
    private int Max_refil;
    public String Name;


    Database con;


    //Constructor
    public Importer(String Iid) {
        this.Uid = Iid;

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

                String query2 = "SELECT * FROM `importers` WHERE `Uid` =?  ";
                try {
                    ps2 = Database.Connect().prepareStatement(query2);

                    ps2.setInt(1, id);

                    rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        this.ID = rs2.getInt("ID");
                        this.Stock_level = getStock_level();
                        this.Wallet = getBal();
                        this.Name = rs2.getString("IName");
                        this.Max_refil = rs2.getInt("Max_refil");

                          System.out.println("Welcome : "+this.Name);
                    }
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }else{
                this.Wallet = 0;

            }


        } catch (SQLException e) {
            e.printStackTrace();


        }
    }



    public int newImport(int amount) {

        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;

        String query = "SELECT * FROM `warehouse` WHERE `Imp_id` =?  ";

        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setInt(1, this.ID);

            rs = ps.executeQuery();
            if (rs.next()) {
                int stck = rs.getInt("Stock_level");
                int price = rs.getInt("Price");
                int max= this.Max_refil;

                int nstck = stck + amount;
                double fprice = amount * price;
                double wallet = getBal();
                double nwallet = wallet - fprice;
                System.out.println(nwallet);

                if(wallet < fprice ) {
                    System.out.println("Insufficient Wallet Balance \n");
                }else if (stck >= max){
                    System.out.println("You Have Not reached level to Import \n");
                }else{
                    updateWallet(nwallet);
                    updateStock(nstck);

                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return 0;
    }

    public int updateStock(int new_stock) {


        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;

        String query = "UPDATE warehouse SET Stock_level = ? WHERE `Imp_id` =?  ";

        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setInt(1, new_stock);
            ps.setInt(2, this.ID);

             ps.executeUpdate();

             System.out.println("Stock Updated\n");
             return 1;

        }catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("Stock Not Updated\n");
            return 0;
        }

    }
    public int updateWallet(double new_bal) {


        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;

        String query = "UPDATE importers SET Wallet = ? WHERE `ID` =?  ";

        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setDouble(1, new_bal);
            ps.setInt(2, this.ID);

            ps.executeUpdate();

            System.out.println("Wallet Updated\n");
            return 1;

        }catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("Wallet Not Updated\n");
            return 0;
        }
    }

    public int getMax_refil()
    {
        return this.Max_refil;
    }

    public int getLDbal(int LDid){
        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;

        String query = "SELECT * FROM `localdistributors` WHERE `Uid` =?  ";

        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setInt(1, LDid);

            rs = ps.executeQuery();
            if (rs.next()) {
               return rs.getInt("Wallet");
            }else {
                System.out.println("Distributor Unknown !!");
                return 0;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }

    public int topUp(int LDid, int amount){

        con = new Database();
        PreparedStatement ps;
        ResultSet rs;

        String query = "Update localdistributors SET Wallet = ? where Uid = ? ";

        try {
            ps = Database.Connect().prepareStatement(query);
            int ldbal = getLDbal(LDid);
            int newWallet = ldbal + amount;
            ps.setInt(1, newWallet);
            ps.setInt(2, LDid);

            ps.executeUpdate();
            System.out.println("TopUp Successfull !!\n");
            return 1;



        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;

        }

    }

    public int getBal() {
        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;

        String query = "SELECT * FROM `importers` WHERE `ID` =?  ";

        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setInt(1, this.ID);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Wallet");
            }
        }catch (SQLException ex){
            ex.printStackTrace();
            return 0;
        }
        return 0;
    }

    public int getStock_level() {


        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;

        String query = "SELECT * FROM `warehouse` WHERE `ID` =?  ";

        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setInt(1, this.ID);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Stock_level");
            }
        }catch (SQLException ex){
            ex.printStackTrace();
            return 0;
        }
        return 0;
    }

    public boolean requestImport(WareHouse wareHouse, int Uid) {

        return true;
    }




}

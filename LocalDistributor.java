import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocalDistributor {

    private String  Uid;
    public int ID;
    public String Name;
    public int Amount;
    public int Max_amt;
    public double Price;
    private String Password;
    private int Wallet;

    static Database con;

    public LocalDistributor(String Uid, String Password) {
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

                System.out.println(id);

                String query2 = "SELECT * FROM `localdistributors` WHERE `Uid` =?  ";
try {
    ps2 = Database.Connect().prepareStatement(query2);

    ps2.setInt(1, id);

    rs2 = ps2.executeQuery();
    if (rs2.next()) {
         this.ID = rs2.getInt("ID");
        this.Amount = getAmount();
        this.Name = rs2.getString("LDname");
        this.Price = rs2.getDouble("Price");
        this.Max_amt = rs2.getInt("Max_refil");
        this.Wallet = rs2.getInt("Wallet");

        System.out.println("Welcome : "+this.Name);
    }
}catch (SQLException ex){
    ex.printStackTrace();
}
            }else{
                this.Amount = 0;

            }


        } catch (SQLException e) {
            e.printStackTrace();


        }
    }
    public int getAmount(){
        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;

        String query = "SELECT * FROM `localdistributors` WHERE `ID` =?  ";

        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setInt(1, this.ID);

            rs = ps.executeQuery();
            if (rs.next()) {
                return  rs.getInt("Stock_level");
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }
    public int getStock(){
        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;

        String query = "SELECT * FROM `localdistributors` WHERE `ID` =?  ";

        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setInt(1, this.ID);

            rs = ps.executeQuery();
            if (rs.next()) {
                return  rs.getInt("Stock_level");
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }

    public int getMaxAmnt() {

        return this.Max_amt;
    }

    public boolean login(int LDid, String pass) {

        return true;
    }

    public boolean restock(int amount, int  Iid) {
        if(getAmount() < this.Max_amt) {
            int ci = checkImporter_stock(Iid, amount);
            System.out.println("Importer Stock :" + ci);


            int newamt = this.Amount + amount, inewamt = ci - amount;
            this.Amount = newamt;

            if (update(inewamt, Iid) && update(newamt, 0)) {
                System.out.println("Stock Updated :" + newamt);
                return true;


            } else {
                System.out.println("Importer Unable to Restock\n");
                return false;
            }
        }else {
            System.out.println("You Stock Is Above Restock Amount\n");
            return false;
        }

    }

        public  boolean update(int amount, int Iid){
            con = new Database();
            PreparedStatement ps;
            int rs;

            int Uid;
            String Uid2,query;

            try {
                if(Iid == 0 ) {
                    query = "Update localdistributors SET Stock_level = ? where ID = ? ";
                    Uid = this.ID;



                }else{
                    query = "Update warehouse SET Stock_level = ? where Imp_id = ? ";
                    Uid = Iid;


                }

                ps = Database.Connect().prepareStatement(query);

                ps.setInt(1, amount);
                ps.setInt(2, Uid);

                 ps.executeUpdate();

                    System.out.println("Restock Successfull !!");
                    return true;





            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;

            }
        }






    public int checkImporter_stock(int  Iid, int amount) {
        con = new Database();
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT * FROM `warehouse` WHERE `Imp_id` =?  ";

        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setInt(1, Iid);

            rs = ps.executeQuery();
            if(rs.next()) {
                int imp_stock = rs.getInt("Stock_level");
                if(amount > imp_stock) {

                    return 0;
                }else{
                    return imp_stock;
                }

            }else{

                return 0;

            }


        } catch (SQLException e) {
            e.printStackTrace();

            return 0;
        }
    }

    public int getCid(String Cid) {
        con = new Database();
        PreparedStatement ps2;
        ResultSet rs2;

        String query = "SELECT * FROM `Users` WHERE `Uid` =? ";
        try {
            ps2 = Database.Connect().prepareStatement(query);

            ps2.setString(1, Cid);

            rs2 = ps2.executeQuery();
            if (rs2.next()) {
                return rs2.getInt("ID");
            }else {
                return 0;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
            return 0;
        }

    }

    public int getCwallet(int Cid) {
        con = new Database();
        PreparedStatement ps2;
        ResultSet rs2;

        String query = "SELECT * FROM `consumers` WHERE `Uid` =? ";
        try {
            ps2 = Database.Connect().prepareStatement(query);

            ps2.setInt(1, Cid);

            rs2 = ps2.executeQuery();
            if (rs2.next()) {
                return rs2.getInt("Wallet");
            }else {
                return 0;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
            return 0;
        }

    }

    public boolean topup(int amount, String CId) {
        con = new Database();
        PreparedStatement ps;
        ResultSet rs;

        String query = "Update consumers SET Wallet = ? where Uid = ? ";
        int Cid = getCid(CId);
        int Cwallet = getCwallet(Cid);
        int newWallet = Cwallet + amount;
        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setInt(1, newWallet);
            ps.setInt(2, Cid);

            ps.executeUpdate();
                return true;



        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;

        }

    }

    public int checkWallet(){return this.Wallet;}

}

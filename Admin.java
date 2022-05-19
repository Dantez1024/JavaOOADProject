
import java.sql.DataTruncation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;


public class Admin {

    private int ID;
    private  String Uid;

    Database con;




    public Admin(String Uid){
        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;

        String query = "SELECT * FROM `users` WHERE `Uid` =?  ";

        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setString(1, Uid);

            rs = ps.executeQuery();
            if (rs.next()) {
                this.ID=rs.getInt("ID");
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public int addUser(String name,String password,int type ,int impMax, int ldMax,double impPrice , double LDprice){
        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;
        Random rand =new Random();
        int upp = 100;

        int init_rnd = rand.nextInt(upp);

        String Uid2 = checkUid(init_rnd,type);
        String query = "INSERT  INTO users (Uid,Pass,Login_status,user_type) values (?,?,?,?)    ";


        try {
            ps = con.Connect().prepareStatement(query);
            ps.setString(1, Uid2);
            ps.setString(2, password);
            ps.setInt(3, 0);
            ps.setInt(4, type);

            ps.execute();
            System.out.println("User Added\n");
            System.out.println("User ID :"+Uid2+"\n");
            if(type == 1){
                addConsumer(name,Uid2);
            }else if (type == 2){
                addLD(name,ldMax,Uid2,LDprice);
            }else if (type == 3){
                addImporter(name,impMax,Uid2,impPrice);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }

    public int getUid(String uid){
        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;

        String query = "SELECT * FROM `users` WHERE `Uid` =?  ";

        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setString(1, uid);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }

    public int addConsumer(String name,String  uid){

        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;

        String query = "INSERT  INTO consumers (Uid,Cname,Wallet) values (?,?,?)    ";

        int uid2=getUid(uid);

        try {
            ps = con.Connect().prepareStatement(query);
            ps.setInt(1,uid2);
            ps.setString(2,name);
            ps.setInt(3,0);



             ps.execute();
             System.out.println("Consumer Added");
            return 1;
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }




    public void addImporter(String name,int max,String uid,double price){
        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;

        String query = "INSERT  INTO importers (Uid,IName,Max_refil,Price) values (?,?,?,?)    ";

        int uid2=getUid(uid);
        int id = getImpID(uid);
        try {
            ps = con.Connect().prepareStatement(query);
            ps.setInt(1,uid2);
            ps.setString(2,name);
            ps.setInt(3,max);
            ps.setDouble(4,price);



            ps.execute();
            addWarehouse(id);
            System.out.println("Importer Added\n");

        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }
    public void addLD(String name,int max,String uid,double price){
        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;

        String query = "INSERT  INTO localdistributors (Uid,LDName,Max_refil,Price) values (?,?,?,?)    ";
        int uid2=getUid(uid);
        try {
            ps = con.Connect().prepareStatement(query);
            ps.setInt(1,uid2);
            ps.setString(2,name);
            ps.setInt(3,max);
            ps.setDouble(4,price);


            ps.execute();
            System.out.println("Distributor Added");

        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void addWarehouse(int ImpID){
        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;

        String query = "SELECT * FROM `importers` WHERE `Uid` =?  ";
System.out.println(Uid);
        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setInt(1, ImpID);

            rs = ps.executeQuery();
            if (rs.next()) {
                String query2 = "INSERT  INTO warehouse (Imp_id,Price) values (?,?)    ";

                try {
                    ps2 = con.Connect().prepareStatement(query2);
                    ps2.setInt(1,rs.getInt("ID"));
                    ps2.setInt(2,1);


                    ps2.execute();
                    System.out.println("Importer Added To Warehouse");

                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    public int checkImpBal(int Impid){
        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;

        String query = "SELECT * FROM `importers` WHERE `ID` =?  ";

        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setInt(1, Impid);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Wallet");
            }else {
                System.out.println("Importer Unknown !!");
                return 0;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }

    public int  getImpID(String  Uid){
        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;
        String query = "SELECT * FROM `users` WHERE `Uid` =?  ";

        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setString(1, Uid);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }

    public String  checkUid(int Uid,int type){
        con = new Database();
        PreparedStatement ps,ps2;
        ResultSet rs,rs2;
        String UID="";
        if(type == 1) {
            UID = "c" + Uid;
        }else if (type == 2){
            UID = "l" + Uid;
        }else if (type == 3){
            UID = "i" + Uid;
        }
        String query = "SELECT * FROM `users` WHERE `Uid` =?  ";

        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setString(1, UID);

            rs = ps.executeQuery();
            if (rs.next()) {
                Random rand =new Random();
                int upp = 100;

                int init_rnd = rand.nextInt(upp);
                checkUid(init_rnd,type);
            }else {

                return UID;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return "";
    }

    public int topUp(int Impid, int amnt) {
        con = new Database();
        PreparedStatement ps;
        ResultSet rs;

        String query = "Update importers SET Wallet = ? where ID = ? ";

        int Cwallet = checkImpBal(Impid);
        int newWallet = Cwallet + amnt;
        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setInt(1, newWallet);
            ps.setInt(2, Impid);

            ps.executeUpdate();
            System.out.println("Topup Successfull\n");
            return 1;



        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;

        }

    }
}

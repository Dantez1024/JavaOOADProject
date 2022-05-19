import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AntiHoardingCLI {

    static Database con;
    static Consumer cons;
    static LocalDistributor Ld;
    static Importer imp;
    static Admin adm;

    static String Uid="";





    public static void main(String[] args) {

        int choice, choice2;

        System.out.println("Hello and Welcome to AHS\n");
         showMenu(0);




    }



    public static void showMenu(int type) {

        String  user, pass;
        int amount, option, login,Iid,LDid;
        Scanner input = new Scanner(System.in);
        if(type == 0) {
            if(Uid != "") {
                logout(Uid);
            }
            System.out.println("Enter User ID : \n");
            Uid  = input.nextLine();
            System.out.println("Enter User Password : \n");
            pass = input.nextLine();

            login = login(Uid, pass);

            if(login != 0) {
                if(login == 1) {
                     cons = new Consumer(Uid, pass);
                     System.out.println("Welcome : "+ cons.getName()+"\n");
                     showMenu(2);
                }else if(login == 2) {
                    Ld = new LocalDistributor(Uid, pass);
                    showMenu(3);
                }else if(login == 3){
                    imp = new Importer(Uid);
                    showMenu(6);
                }else if(login == 4){
                    adm = new Admin(Uid);
                    showMenu(9);
                }

            }else{
                System.out.println("Wrong Details !! Try Again\n");
                showMenu(0);
            }


        }else if(type == 1) {


           if( cons.checkWallet()) {
               Scanner input2 = new Scanner(System.in);

               System.out.println("Enter Fuel Litres : \n");
               amount = input.nextInt();
               System.out.println("Enter Seller ID: \n");
               LDid = input2.nextInt();
               cons.buy(LDid, amount);
               showMenu(2);
           }else{
               System.out.println("You Have Insufficient Funds To Buy Fuel !!\nVisit an agent to top up\n");
               showMenu(2);
           }

        }else if(type == 2) {


            do {
                System.out.println("Choose one option below :\n");
                System.out.println("1.Buy Fuel\n");
                System.out.println("2.Check Wallet Balance\n");
                System.out.println("3.Exit\n");
                option = input.nextInt();
            } while (option < 1 || option > 3);

            if (option == 1){
                showMenu(1);
            }else if(option == 2){

                System.out.println("Your Wallet Balance :"+cons.getWalletBAlance());
                showMenu(2);
            }else {
                showMenu(0);
            }
            option = 0;
        }else if(type == 3) { //Local Distributor Home Interface

            do {
                System.out.println("Choose one option below :\n");
                System.out.println("1.Restock \n");
                System.out.println("2.Topup Client\n");
                System.out.println("3.Check Wallet Balance\n");
                System.out.println("4.Check Stock Level\n");
                System.out.println("5.Exit\n");
                option = input.nextInt();
            }while (option < 1 || option > 5);

            if(option == 1){
                showMenu(4);
            }else if(option == 2) {
                showMenu(5);
            }else if (option == 3){
                System.out.println("Your Balance Is :"+Ld.checkWallet());
                showMenu(3);
            }else if(option == 4){
               System.out.println("Your Stock Level Is: "+Ld.getStock());
                showMenu(3);
            }else {
                showMenu(0);
            }

        }else if(type == 4) {
            amount = 0;Iid=0;
            Scanner input2 = new Scanner(System.in);
            do {

                System.out.println("Enter Importer ID : \n");
                Iid = input2.nextInt();
                System.out.println("Enter Amount :\n");
                amount = input.nextInt();
            }while ( Iid ==0 && amount == 0);
System.out.println(Iid+"    "+amount);
           if (Ld.checkImporter_stock(Iid, amount) > 0) {
               System.out.println(Ld.checkImporter_stock(Iid,amount));
               Ld.restock(amount, Iid);
               showMenu(3);
           }else{

               System.out.println("Restock Error!!\n");
               showMenu(3);
           }





        }else if (type == 5) {

            System.out.println("Enter Consumer ID:\n");
            String Cid = input.nextLine();
            System.out.println("Enter TopUp Amount :\n");
            int Camount = input.nextInt();

            if(Ld.topup(Camount,Cid)){
                System.out.println("Topup Successfull");
                showMenu(3);
            }else {
                System.out.println("Topup failed !! Try Again\n");
                showMenu(5);
            }


        }else if (type == 6){
            System.out.println("Choose One Option Below :\n");
            System.out.println("1.Import Fuel\n");
            System.out.println("2.Check Stock Level\n");
            System.out.println("3.Check Wallet Balance\n");
            System.out.println("4.Topup Distributor\n");
            System.out.println("5.Exit\n");

            option = input.nextInt();

            if(option == 1){
                showMenu(7);
            }else if (option == 2){
                System.out.println("Your Stock Level : "+imp.getStock_level());
                showMenu(6);
            }else if (option == 3){
                System.out.println("Your Wallet Balance : "+imp.getBal());
                showMenu(6);
            }else if (option == 4){
                showMenu(8);
            }else{
                showMenu(0);
            }
        }else if (type == 7){
            int nestck;
            System.out.println("Enter New Stock :");
            nestck = input.nextInt();

            imp.newImport(nestck);

            showMenu(6);

        }else if (type == 8){
            System.out.println("Enter Distributor ID: \n");
            LDid = input.nextInt();
            System.out.println("Enter TopUp Amount :\n");
            amount = input.nextInt();

            imp.topUp(LDid, amount);
            showMenu(6);
        }else if(type == 9){
            do {
                System.out.println("Choose One Option Below :\n");
                System.out.println("1.Add Consumer \n");
                System.out.println("2.Add LocalDistributor\n");
                System.out.println("3.Add Importer\n");
                System.out.println("4.TopUp Importer\n");
                System.out.println("5.Exit\n");

                option = input.nextInt();
            }while (option <0 || option >5);


            if(option == 1){
                showMenu(10);
            }else if(option == 2){
                showMenu(11);
            }else if (option == 3){
                showMenu(12);
            }else if(option == 4){
                showMenu(13);
            }else {
                showMenu(0);
            }
        }else if(type == 10){
            System.out.println("Enter Consumer Name:\n");
            String Cname = input.nextLine();
            System.out.println("Enter Password :\n");
            String Cpass = input.nextLine();

            adm.addUser(Cname,Cpass,1,0,0,0,0);
            showMenu(9);


        }else if(type == 11){
            System.out.println("Enter LocalDistributor Name:\n");
            String Cname = input.nextLine();
            System.out.println("Enter Password :\n");
            String Cpass = input.nextLine();
            System.out.println("Enter Max Refil Amount :\n");
            int max = input.nextInt();
            System.out.println("Enter Price :\n");
            double ldprice = input.nextDouble();



            System.out.println(adm.addUser(Cname,Cpass,2,0,max,0,ldprice));
            showMenu(9);

        }else if(type == 12){
            System.out.println("Enter Importer Name:\n");
            String Cname = input.nextLine();
            System.out.println("Enter Password :\n");
            String Cpass = input.nextLine();
            System.out.println("Enter Max Refil Amount :\n");
            int max = input.nextInt();
            System.out.println("Enter Price :\n");
            double impprice = input.nextDouble();



            adm.addUser(Cname,Cpass,3,max,0,impprice,0);
            showMenu(9);

        }else if(type == 13){
            System.out.println("Enter importer ID:\n");
            int Impid = input.nextInt();
            System.out.println("Enter Amount :\n");
            int amnt = input.nextInt();
            adm.topUp(Impid,amnt);
            showMenu(9);
        }
    }

    public static int checkType(String Uid) {

            return 1;

    }

    public static int login(String Uid, String Password) {

        System.out.println("\nVerifying , Please wait !!\n");
        con = new Database();
        PreparedStatement ps;
        ResultSet rs;
        int user_type;

        String query = "SELECT * FROM `Users` WHERE `Uid` =? AND `pass` =? AND  `login_status`=?";

        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setString(1, Uid);
            ps.setString(2, Password);
            ps.setInt(3,0);

            rs = ps.executeQuery();
            if(rs.next()) {


                return rs.getInt("user_type");
            }else{
                return 0;
            }


        } catch (SQLException e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());

            return 0;
        }


    }
    public static int logout(String Uid){
        con = new Database();
        PreparedStatement ps;
        ResultSet rs;

        String query = "Update users SET login_status = ? where Uid = ? ";



        try {
            ps = Database.Connect().prepareStatement(query);

            ps.setInt(1, 0);
            ps.setString(2, Uid);

            ps.executeUpdate();
            System.out.println("Logged Out !!\nBye !!\n");
            return 1;



        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;

        }
    }
}

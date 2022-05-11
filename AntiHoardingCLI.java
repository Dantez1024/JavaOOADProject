import java.util.Scanner;


import java.sql.*;

public class AntiHoardingCLI {


    public static void main(String[] args) {

        int choice, choice2;

         showMenu(0, 'q');




    }



    public static void showMenu(int type, char option) {
        int choice = 0, choice2;
        char choice3  , choice4;

        if(type == 0) {
            do {
                System.out.println("Hello And Welcome !!\n");
                System.out.println("Choose an option below to continue :\nI am a :\n");
                System.out.println("1.Consumer\n");
                System.out.println("2.Local Distributor\n");
                System.out.println("3.Importer\n");
                System.out.println("Enter Option :\n");

                Scanner keyboard = new Scanner(System.in);
                choice = keyboard.nextInt();
            }while(choice >3 || choice <1);
             showMenu(choice, 'q');
        }else if(type == 1) {
            if(option == 'q') {
                do {
                    System.out.println("Hello Consumer !!\n");
                    System.out.println("Choose an option below to continue :\nI am a :\n");
                    System.out.println("a.Login\n");
                    System.out.println("b.Register.\n");
                    System.out.println("Enter Option :\n");

                    Scanner keyboard = new Scanner(System.in);
                    choice3 = keyboard.next().charAt(0);
                } while (choice3 != 'a' && choice3 != 'b');
                showMenu(type, choice3);
            }else{
                Scanner input = new Scanner(System.in);

                int sellerID, amount;
                String  user,pass;
                if(option == 'a') {

                        System.out.println("Enter your User ID:\n");
                        user = input.nextLine();
                        System.out.println("Enter your Password:\n");
                        pass = input.nextLine();

                        Consumer cons =new Consumer(user,pass);

                        if(cons.login()){
                            System.out.println("Enter Seller ID:\n");
                            sellerID = input.nextInt();
                            System.out.println("Enter Amount: \n");
                            amount = input.nextInt();

                           if(cons.buy(sellerID, amount))
                            {
                                System.out.println();
                            }

                        }else {
                            System.out.println("Wrong Details Try Again");
                            showMenu(type, 'a');
                        }



                }

            }

        }else if(type == 2) {

            do {
                System.out.println("Hello Local Distributor !!\n");
                System.out.println("Choose an option below to continue :\nI am a :\n");
                System.out.println("a.Login\n");
                System.out.println("b.Register\n");
                System.out.println("Enter Option :\n");

                Scanner keyboard = new Scanner(System.in);
                choice3 = keyboard.next().charAt(0);;
                if(choice3 == 'a' || choice3 == 'b'){
                    break;
                }
            }while(choice3 != 'a' && choice3 != 'b');
            //LocalDistributor.dosomething
        }else if(type == 3){

            if(option == 'q') {
                do {
                    System.out.println("Hello Importer\n");
                    System.out.println("Choose an option below to continue :\nI am a :\n");
                    System.out.println("a.Login\n");
                    System.out.println("b.Register\n");
                    System.out.println("Enter Option :\n");

                    Scanner keyboard = new Scanner(System.in);
                    choice3 = keyboard.next().charAt(0);
                    ;
                    System.out.println(choice3);


                } while (choice3 != 'a' && choice3 != 'b');
                showMenu(3, choice3);
            }else{
                if(option == 'a'){


                }else{

                }
            }

        }

    }

    public boolean login(int type, int Uid, String pass) {

        if(type == 1 ){

        }
        return true;
    }
}

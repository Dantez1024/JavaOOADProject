public class Importer {

    private int Uid;
    public String Iname;
    public String date;
    public WareHouse Amount;
    public int Max_Amount;
    public String Pasword;


    //Constructor
    public Importer(int ID, String name, String date, WareHouse amount, int max_Amount) {
        this.Iname = name;
        this.Uid = ID;
        this.date = date;
        this.Max_Amount = max_Amount;
        this.Amount = amount;
    }

    public boolean login(int LDid, String pass) {

        return true;
    }

    public int getMax_Amount()
    {
        return this.Max_Amount;
    }

    public boolean requestImport(WareHouse wareHouse, int Uid) {

        return true;
    }




}

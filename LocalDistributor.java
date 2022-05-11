public class LocalDistributor {

    private int Uid;
    public String Name;
    public int Amount;
    public int Max_amt;
    private String Password;

    public LocalDistributor(String name, int amount, int max_amt) {
        this.Name = name;
        this.Amount = amount;
        this.Max_amt = max_amt;
    }

    public int getMaxAmnt() {

        return this.Max_amt;
    }

    public boolean login(int LDid, String pass) {

        return true;
    }
}

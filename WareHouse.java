public class WareHouse {

    private int importer;
    private int Amount;
    private int warehouseID = 0;

    public WareHouse(int importer, int amount) {
        this.importer = importer;
        this.Amount = amount;
        this.warehouseID++;
    }

    public boolean store(int type, int uid, int amount) {

        return true;
    }

}

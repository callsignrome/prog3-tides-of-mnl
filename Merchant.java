public class Merchant {
    private String name;
    private int capital;
    private int gold;
    private double targetProfit;
    private Vessel vessel;
    private Port currentPort;

    public Merchant(String name, int capital, double targetProfit) {
        this.name = name;
        this.capital = capital;
        this.targetProfit = targetProfit;
        gold = capital;
        vessel = new Vessel();
    }

    public boolean spendGold(int amount) {
        if(amount < 0 || (gold - amount) < 0)
            return false;

        this.gold -= amount;
        return true;
    }

    public void earnGold(int amount) {
        if(amount > 0)
            this.gold += amount;
    }

    public boolean buy(Port port, Good good, int qty) {
        return;
    }

    public boolean sell(Port port, Good good, int qty) {
        return;
    }

    public void moveTo(Port port) {
        currentPort = port;
    }

    public String getName() {
        return name;
    }

    public int getCapital() {
        return capital;
    }

    public int getGold() {
        return gold;
    }

    public double getTargetProfit() {
        return targetProfit;
    }

    /*public Vessel getVessel() {
        return vessel;
    }*/

    public int getProfit() {
        return getGold() - getCapital();
    }

    public double getProfitPercent() {
        return (getProfit() / getCapital()) * 100;
    }
}
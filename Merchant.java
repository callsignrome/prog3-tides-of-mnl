/**
 * Merchant.java
 * PURPOSE OF THIS FILE: Defines the player (the merchant) within the game. 
 *                       It covers the player's financial state in terms of gold, starting capital, target profit. At the same
 *                       time, it tracks their current location and vessel, and handles the core actions for buying and selling goods.
 */

/**
 * The player.
 * The profit is never stored as it is always computed as the gold minus starting capital
 */
public class Merchant {
    /** Name of the Merchant */
    private String name;
    /** Gold that the merchant started with */
    private int capital;
    /** Gold currently held */
    private int gold;
    /** Target profit as a percentage of the Starting capital */
    private double targetProfit;
    /** The merchant's vessel */
    private Vessel vessel;
    /** The port where the merchant is currently docked at */
    private Port currentPort;

    /**
     * Creates a merchant with an empty vessel. 
     * The merchant will have no port until "#moveTo(Port)" is called.
     *
     * @param name           the name of the merchant
     * @param capital        starting capital in gold
     * @param targetProfit   target profit percentage
     */
    public Merchant(String name, int capital, double targetProfit) {
        this.name = name;
        this.capital = capital;
        this.targetProfit = targetProfit;
        gold = capital;
        vessel = new Vessel();
    }

    /**
     * Deducts gold only if the merchant can afford it.
     *
     * @param amount         gold to spend
     * @return true if spent, false if otherwise
     */
    public boolean spendGold(int amount) {
        if(amount < 0 || (gold - amount) < 0)
            return false;

        this.gold -= amount;
        return true;
    }

    /**
     * Adds gold. (However, negative amounts are ignored)
     *
     * @param amount         gold to add
     */
    public void earnGold(int amount) {
        if(amount > 0)
            this.gold += amount;
    }

    /**
     * Purchases the good/s at the given port's current price.
     *
     * @param port           the port to buy from
     * @param good           the good to buy
     * @param qty            the quantity to buy
     * @return true if the transaction succeeded
     */
    public boolean buy(Port port, Good good, int qty) {
        int price = port.getPrice(good);

        if(qty <= 0 || price < 0 || qty > vessel.getFreeSpace())
            return false;
        if(!spendGold(price * qty))
            return false;
        
        return vessel.load(good, qty);
    }

    /**
     * Sells the good/s at the given port's current price.
     * 
     * @param port            the port to sell to
     * @param good            the goods to sell
     * @param qty             the quantity to sell
     * @return true if the sale succeeded
     */
    public boolean sell(Port port, Good good, int qty) {
        int price = port.getPrice(good);

        if(qty <= 0 || price < 0 || !vessel.unload(good, qty))
            return false;
        
        earnGold(price * qty);
        return true;
    }

    /**
     * Docks the merchant at a port.
     *
     * @param port            the port to dock at
     */
    public void moveTo(Port port) {
        currentPort = port;
    }

    /**
     * Gets the merchant's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the starting capital
     *
     * @return the starting capital in gold
     */
    public int getCapital() {
        return capital;
    }

    /**
     * Gets the gold that is on hand.
     *
     * @return the current gold
     */
    public int getGold() {
        return gold;
    }

    /**
     * Gets the target profit percentage.
     *
     * @return the target profit percentage
     */
    public double getTargetProfit() {
        return targetProfit;
    }

    /**
     * Gets the vessel.
     *
     * @return the merchant's vessel
     */
    public Vessel getVessel() {
        return vessel;
    }

    /**
     * Gets the port where the merchant is docked
     * 
     * @return the current port
     */
    public Port getCurrentPort() {
        return currentPort;
    }

    /**
     * Computes for the profit gained via the formula: Current Gold minus Starting Capital
     *
     * @return the profit in gold (returns negative for a loss)
     */
    public int getProfit() {
        return getGold() - getCapital();
    }

    /**
     * Computes for the profit as a percentage of the starting capital
     * via this formula: Profit divided by Capital multiplied by 100
     * 
     * @return the profit percentage
     */
    public double getProfitPercent() {
        return ((double) getProfit() / getCapital()) * 100;
    }
}

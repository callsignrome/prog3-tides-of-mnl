/**
 *                       Defines a trading port in the game. It also tracks the name of the port,
 *                       the goods it trades using parallel arrays, their respective minimum and maximum 
 *                       price ranges, and the specific prices generated for the merchant's current visit.
 */
public class Port {
    /** Name of the Port */
    private String name;
    /** The goods the port offers for trade (slot i matches the port arrays) */
    private Good[] goods;
    /** Lower price bound of the good in the slot */
    private int[] minPrices;
    /** Upper price bound of the good in the slot */
    private int[] maxPrices;
    /** Price of the good in the slot for the current visit (-1 if there is none yet) */
    private int[] prices;
    /** Number of the slots that are in use */
    private int count;

    /**
     * Creates a standby port that does not currently trade any good/s
     *
     * @param name        name of the port
     */
    public Port(String name) {
        this.name = name;
        goods = new Good[4];
        minPrices = new int[4];
        maxPrices = new int[4];
        prices = new int[4];
        count = 0;
    }
    
    /**
     * Finds the slot of a good.
     * 
     * @param good        the good that is being looked for
     * @return the slot number (-1 if the port does not trade said good)
     */
    private int indexOf(Good good) {
        for (int i = 0; i < count; i++)
            if (goods[i].equals(good))
                return i;
        return -1;
    }

    /**
     * Gets the name of the port.
     * 
     * @return the name of the port
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the range of a good. If the min is larger than the max, the two bounds are swapped so that 
     * the range is always min to max. If there is a fifth different good given, it is ignored since a port trades at most 4 goods.
     * 
     * @param good         the good
     * @param min          the lower bound of the price
     * @param max          the higher bound of the price
     */
    public void setRange(Good good, int min, int max) {
        int i = indexOf(good);
        if (i == -1) {
            if (count >= goods.length) {
                return;
            }
            i = count;
            goods[i] = good;
            prices[i] = -1;
            count++;
        }
        minPrices[i] = Math.min(min, max);
        maxPrices[i] = Math.max(min, max);
    }

    /**
     * Generates a new random price (in both bounds inclusively) for every good the given port trades
     * This is called each time the player arrives at the port.
     */   
    public void generatePrices() {
        for (int i = 0; i < count; i++) {
            int min = minPrices[i];
            int max = maxPrices[i];
            
            prices[i] = min + (int) (Math.random() * ((max - min) + 1));
        }
    }

    /**
     * Gets the price of a good for the current visit.
     *
     * @param good         the good
     * @return the current price (-1 if the good is not traded here or no price has been generated yet)
     */
    public int getPrice(Good good) {
        int i = indexOf(good);
        if (i == -1) {
            return -1;
        }      
        return prices[i];
    }
}

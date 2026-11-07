/**
 * Vessel.java
 * PURPOSE OF THIS FILE: Defines the merchant's vessel and tracks the inventory of goods it carries. At the same time, it enforces
 *                       the maximum cargo capacity limit and provides methods to load or unload quantities of tradable goods.
 */
public class Vessel {
    /** Goods currently carried by the vessel */
    private Good[] goods;
    /** Quantity of each good/s (matches position in goods array). */
    private int[] quantities;
    /** Number of slots currently being used in the arrays. */
    private int count;
    /** The maximum total number of goods the vessel can carry. */
    private static final int CAPACITY = 75;

    /**
     * Creates an empty vessel.
     */
    public Vessel() {
        goods = new Good[4];
        quantities = new int[4];
        count = 0;
    }

    /**
     * Finds the slot of a good.
     *
     * @param good        the good to look for
     * @return the slot number (-1 if the vessel has never held the given good)
     */
    private int indexOf(Good good) {
        for (int i = 0; i < count; i++)
            if (goods[i].equals(good))
                return i;
        return -1;
    }

    /**
     * Gets the capacity of the vessel
     * 
     * @return the maximum number of goods the vessel can carry
     */
    public int getCapacity() {
        return CAPACITY;
    }

    /**
     * Gets how many units of a given good are carried
     *
     * @param good        the good
     * @return the quantity of the good that is being carried (0 if none)
     */
    public int getQuantity(Good good) {
        int i = indexOf(good);
        if (i == -1)
            return 0;
        return quantities[i];
    }

    /**
     * Gets the total number of goods being carried
     *
     * @return the sum of all quantities in the hold
     */
    public int getTotalCargo() {
        int total = 0;
        for (int i = 0; i < count; i++)
            total += quantities[i];
        return total;
    }

    /**
     * Gets the remaining space in the hold
     *
     * @return capacity minus the total cargo
     */
    public int getFreeSpace() {
        return CAPACITY - getTotalCargo();
    }

    /**
     * Loads the goods into the vessel if it can fit
     *
     * @param good        the good
     * @param qty         the quantity to load (minimum of 1, if 0 or below then invalid)
     * @return true if loaded, false if the qty is invalid, exceeds free space, or the vessel already tracks four other kinds of good/s
     */
    public boolean load(Good good, int qty) {
        if (qty <= 0 || qty > getFreeSpace())
            return false;

        int i = indexOf(good);
        if (i == -1) {
            if (count >= goods.length)
                return false;
            goods[count] = good;
            quantities[count] = qty;
            count++;
        } else {
            quantities[i] += qty;
        }
        return true;
    }

    /**
     * Unloads the goods from the vessel if enough are carried.
     * 
     * @param good         the good
     * @param qty          the quantity to load (minimum of 1, if 0 or below then invalid)
     * @return true if unload, false if the qty is invalid or it is more than carried.
     */
    public boolean unload(Good good, int qty) {
        int i = indexOf(good);
        if (qty <=0 || i == -1 || qty > quantities[i]) {
            return false;
        }

        quantities[i] -= qty;
        return true;
    }
}

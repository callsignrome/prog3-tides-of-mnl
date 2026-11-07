import java.util.ArrayList;

/**
 *                       This file acts as the central game engine and state manager. It initializes the world
 *                       (goods, ports, and the merchant), tracks the progression of time (days), handles the core 
 *                       logic for traveling, and evaluates the win/loss conditions to determine when the game ends.
 */
public class Game {
    /** The number of journeys (in days) after which the game ends. */
    private static final int MAX_TURNS = 30;
    /** The number of turns used (Current Day) */
    private int day;
    /** The player */
    private final Merchant merchant;
    /** The four trading posts namely Tondo, Manila, Pandakan, and Sapa */
    private final ArrayList<Port> ports;
    /** The four goods namely Coconut, Rice, Silk, and Gun */
    private final ArrayList<Good> goods;

    /**
     * Creates a new game starting on Day 0. The merchant starts in Manila with an empty vessel, and Manila's prices are then generated.
     *  
     * @param name               the merchant's name 
     * @param capital            the starting capital in gold
     * @param targetProfit       the target profit percentage
     */
    public Game(String name, int capital, double targetProfit) {
        this.day = 0;

        this.goods = new ArrayList<>();
        this.goods.add(new Good("Coconut"));
        this.goods.add(new Good("Rice"));
        this.goods.add(new Good("Silk"));
        this.goods.add(new Good("Gun"));

        Good coconut = goods.get(0);
        Good rice = goods.get(1);
        Good silk = goods.get(2);
        Good gun = goods.get(3);

        this.ports = new ArrayList<>();

        Port tondo = new Port("Tondo");
        tondo.setRange(coconut, 4, 24);
        tondo.setRange(rice, 1, 20);
        tondo.setRange(silk, 48, 68);
        tondo.setRange(gun, 60, 95);
        this.ports.add(tondo);

        Port manila = new Port("Manila");
        manila.setRange(coconut, 3, 18);
        manila.setRange(rice, 5, 20);
        manila.setRange(silk, 24, 39);
        manila.setRange(gun, 65, 84);
        this.ports.add(manila);

        Port pandakan = new Port("Pandakan");
        pandakan.setRange(coconut, 2, 12);
        pandakan.setRange(rice, 4, 14);
        pandakan.setRange(silk, 22, 32);
        pandakan.setRange(gun, 90, 103);
        this.ports.add(pandakan);

        Port sapa = new Port("Sapa");
        sapa.setRange(coconut, 4, 9);
        sapa.setRange(rice, 1, 6);
        sapa.setRange(silk, 17, 22);
        sapa.setRange(gun, 204, 301);
        this.ports.add(sapa);

        this.merchant = new Merchant(name, capital, targetProfit);
        merchant.moveTo(manila);
        manila.generatePrices();
    }

    /**
     * Gets the merchant.
     *
     * @return the player
     */
    public Merchant getMerchant() {
        return merchant;
    }

    /**
     * Gets the ports.
     *
     * @return the list of ports
     */
    public ArrayList<Port> getPorts() {
        return ports;
    }
    
    /**
     * Gets the goods.
     *
     * @return the list of goods
     */
    public ArrayList<Good> getGoods() {
        return goods;
    }

    /**
     * Gets the current day (amount of turns used)
     *
     * @return the day (days/turns elapsed from 0 to the maximum turn)
     */
    public int getDay() {
        return day;
    }
    
    /**
     * Gets the turn limit.
     * 
     * @return the maximum number of turns
     */
    public int getMaxTurns() {
        return MAX_TURNS;
    }

    /**
     * When sailing to another port one turn passes and the day rises by one. The merchant then docks at the destination
     * and the destination generates new prices. The last allowed journey lands on day getMaxTurns().
     *
     * @param dest        the destination port
     * @return true if the journey occurred, false if the game is over wherein the destination is equal to null, the destination is not one of 
     *                    of the game's ports, or the destination is the merchant's current port.
     */
    public boolean travel(Port dest) {
        if (isGameOver() || dest == null || !ports.contains(dest) || dest == merchant.getCurrentPort()) {
            return false;
        }
        merchant.moveTo(dest);
        day++;
        dest.generatePrices();
        return true;
    }

    /**
     * Checks whether the game has ended by attaining the target or by the turn limit being met.
     * (Note: Quitting is handled by the console driver)
     * 
     * @return true if the profit percentage has been met or if the day (turn) limit has been reached
     */
    public boolean isGameOver() {
        return merchant.getProfitPercent() >= merchant.getTargetProfit() || day >= MAX_TURNS;
    }

    /**
     * Creates an end of the game (final) message that summarizes the game's outcome. In so it displays 
     * whether the merchant has won, ran out of time, or quit, followed by the merchant's final gold, total 
     * profit, and the number of days taken to attain such.
     *
     * @return the end of the game message to print
     */
    public String getEndMessage() {
        String result;
        if (merchant.getProfitPercent() >= merchant.getTargetProfit()) {
            result = "Congratulations! You have reached your target profit.";
        } else if (day >= MAX_TURNS) {
            result = "Your " + MAX_TURNS + " turns have run out prior to you reaching your target profit";
        } else {
            result = "You decided to retire from trading";
        }
        return result + String.format("%nFinal gold: %d | Profit: %d (%.2f%%) | Turns used: %d",
                merchant.getGold(), merchant.getProfit(), merchant.getProfitPercent(), day);
    }
}        

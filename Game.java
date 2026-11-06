/**
 * Game.java
 * PURPOSE OF THIS FILE: This file acts as the central game engine and state manager. It initializes the world 
 *                       (goods, ports, and the merchant), tracks the progression of time (days), handles the core 
 *                       logic for traveling, and evaluates the win/loss conditions to determine when the game ends.
 */

import java.util.ArrayList;

/**
 * Main game logic controller
 *     Starts on day 0
 *     Ends at "getMaxTurns()"
 *     Time only advances using #travel(Port) where in one journey is one turn and day.
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
     * Gets the current day (amount of turns used)
     *
     * @return the day (days/turns elapsed from 0 to the maximum turn
     */
    public ArrayList<Good> getGoods() {
        return goods;
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
 * @return true if the journey
 */

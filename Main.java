/**
 * Main.java
 * PURPOSE OF THIS FILE: Serves as the console driver and text based interface of the game.
 *                       Particularly, it handles all of the user input and validation, displays
 *                       the main game interface and connects all player choices which include
 *                       buying, selling, travelling or quitting to the game model.
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Console driver for the game.
 */ 
public class Main {
    /** Ensures program starts from Main */
    private Main() {
    }
    
    /** Keyboard reader */
    private static final Scanner INPUT = new Scanner(System.in);

    /**
     * Checks for the merchant's name
     *
     * @param name            the name given (text entered)
     * @return true if it is not null and not blank
     */
    static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    /** 
     * Checks for the starting capital
     * 
     * @param capital         the amount entered
     * @return true if it is greater than zero
     */
    static boolean isValidCapital(int capital) {
        return capital > 0;
    }

    /**
     * Checks for the target profit percentage
     *
     * @param target          the percentage entered
     * @return true if it is at least 20
     */
    static boolean isValidTarget(double target) {
        return target >= 20;
    }

    /**
     * Starts the game
     *
     * @param args            command-line arguments are excluded
     */
    public static void main(String[] args) {
        System.out.println("=== TIDES OF MANILA : A MERCHANT'S QUEST ===");
        String name = askName();
        int capital = askCapital();
        double target = askTarget();
        Game game = new Game(name, capital, target);
        System.out.println("Your journey begins at Manila");

        boolean quit = false;
        while (!game.isGameOver() && !quit) {
            showScreen(game);
            System.out.print("Choose:  [1] Buy  |  [2] Sell  |  [3] Go to another port  |  [4] Quit: ");
            String choice = INPUT.nextLine().trim();
            if (choice.equals("1")) {
                trade(game, true);
            } else if (choice.equals("2")) {
                trade(game, false);
            } else if (choice.equals("3")) {
                travel(game);
            } else if (choice.equals("4")) {
                quit = true;
            } else {
                System.out.println("Input Error: please enter 1, 2, 3 or 4.");
            }
        }
        System.out.println();
        System.out.println("=== GAME OVER ===");
        System.out.println(game.getEndMessage());
    }

    /**
     * Repeatedly asks the user for a valid merchant's name
     *
     * @return the trimmed name (name after removing extra/excess spaces)
     */
    private static String askName() {
        while(true) {
            System.out.print("Enter the Merchant's name: ");
            String name = INPUT.nextLine();
            if (isValidName(name)) {
                return name.trim();
            }
            System.out.println("Input Error: the given name cannot be empty");
        }
    }

    /**
     * Repeatedly asks the user for a valid starting capital
     *
     * @return the capital
     */
    private static int askCapital() {
        while (true) {
            System.out.print("Enter the starting capital in gold (any whole number above 0): ");
            try {
                int capital = Integer.parseInt(INPUT.nextLine().trim());
                if (isValidCapital(capital)) {
                    return capital;
                }
                System.out.println("Input Error: the capital must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Input Error: please enter a whole number.");
            }
        }
    }

    /**
     * Repeatedly asks the user for a valid target profit percentage
     *
     * @return the target percentage
     */
    private static double askTarget() {
        while (true) {
            System.out.print("Enter the target profit percentage (at least 20): ");
            try {
                double target = Double.parseDouble(INPUT.nextLine().trim());
                if (isValidTarget(target)) {
                    return target;
                }
                System.out.println("Input Error: the target must be at least 20%.");
            } catch (NumberFormatException e) {
                System.out.println("Input Error: please enter a number.");
            }
        }
    }

    /**
     * This prints the main screen which include merchant, port day, gold, profit, cargo, and the market prices.
     *
     * @param game            the game to display
     */
    private static void showScreen(Game game) {
        Merchant m = game.getMerchant();
        Port port = m.getCurrentPort();
        System.out.println();
        System.out.println("----------------------------------");
        System.out.printf("Merchant: %s | Port: %s | Day: %d/%d%n", m.getName(), port.getName(), game.getDay(), game.getMaxTurns());
        System.out.printf("Gold: %d | Profit: %d (%.2f%%) | Target: %.2f%%%n", m.getGold(), m.getProfit(), m.getProfitPercent(), m.getTargetProfit());
        System.out.printf("Cargo (%d/%d):%n", m.getVessel().getTotalCargo(), m.getVessel().getCapacity());
        for (Good good : game.getGoods()) {
            System.out.printf("  %-8s x%d%n", good.getName(), m.getVessel().getQuantity(good));
        }
        System.out.println("Market prices here:");
        for (Good good : game.getGoods()) {
            System.out.printf("  %-8s %d gold%n", good.getName(), port.getPrice(good));
        }
    }

    /**
     * This handles the buy and sell mechanic where in it asks for the good and quantity of it
     * then calls the merchant and reports the result or reason for failure.
     * 
     * @param game            the game
     * @param buying          true if buying, false if selling
     */
    private static void trade(Game game, boolean buying) {
        Merchant m = game.getMerchant();
        Port port = m.getCurrentPort();
        if (buying) {
            System.out.println("Select a good to buy:");
        } else {
            System.out.println("Select a good to sell:");
        }
        for (int i = 0; i < game.getGoods().size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + game.getGoods().get(i).getName());
        }
        Integer pick = readInt("Good: ");
        if (pick == null) {
            return;
        }
        if (pick < 1 || pick > game.getGoods().size()) {
            System.out.println("Input Error: that is not a valid good.");
            return;
        }
        Good good = game.getGoods().get(pick - 1);
        Integer qty = readInt("Quantity: ");
        if (qty == null) {
            return;
        }

        if (qty <= 0) {
            System.out.println("Input Error: the quantity must be at least 1.");
        } else if (buying && qty > m.getVessel().getFreeSpace()) {
            System.out.println("Error: not enough cargo space. Free space: " + m.getVessel().getFreeSpace() + ".");
        } else if (buying && (long) qty * port.getPrice(good) > m.getGold()) {
            System.out.println("Error: you do not have enough gold to buy that. Cost: " + (long) qty * port.getPrice(good) + ", you have " + m.getGold() + ".");
        } else if (!buying && qty > m.getVessel().getQuantity(good)) {
            System.out.println("Error: you only carry " + m.getVessel().getQuantity(good) + " " + good.getName() + ".");
        } else if (buying && m.buy(port, good, qty)) {
            System.out.println("Successfully bought " + qty + " " + good.getName() + " for " + qty * port.getPrice(good) + " gold.");
        } else if (!buying && m.sell(port, good, qty)) {
            System.out.println("Successfully sold " + qty + " " + good.getName() + " for " + qty * port.getPrice(good) + " gold.");
        } else {
            System.out.println("Error: the transaction failed.");
        }
    }

    /**
     * This handles the travel mechanic where it lists all the ports and asks the user for one to sail to
     *
     * @param game            the game
     */
    private static void travel(Game game) {
        ArrayList<Port> options = new ArrayList<>(game.getPorts());
        options.remove(game.getMerchant().getCurrentPort());
        System.out.println("Travel to:");
        for (int i = 0; i < options.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + options.get(i).getName());
        }
        Integer pick = readInt("Port: ");
        if (pick == null) {
            return;
        }
        if (pick < 1 || pick > options.size()) {
            System.out.println("Input Error: that is not a valid port.");
            return;
        }
        if (game.travel(options.get(pick - 1))) {
            System.out.println("Arrived at " + options.get(pick - 1).getName()
                    + ". It is now day " + game.getDay() + ". New market prices are set.");
        } else {
            System.out.println("Error: the journey could not be made.");
        }
    }

    /**
     * This reads whole numbers then prints an error if the input isn't one
     *
     * @param prompt            the prompt to show
     * @return the number (or null if the input was not a whole number)
     */
    private static Integer readInt (String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(INPUT.nextLine().trim());
        }
        catch (NumberFormatException e) {
            System.out.println("Input Error: please enter a whole number.");
            return null;
        }
    }
}

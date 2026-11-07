/**
 *                       Defines a tradable good (Coconut, Rice, Silk, or Gun) within the game,
 *                       then stores its name so that it can be priced and traded at different ports.
 */
public class Good {
    /** Name of the good */
    private String name;

    /**
     * Initializes a new good with the given name.
     *
     * @param name    name of the good.
     */
    public Good(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the tradeable good.
     *
     * @return name of the good.
     */
    public String getName() {
        return name;
    }
}

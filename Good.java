/**
 * Good.java
 * PURPOSE OF THIS FILE: Defines a tradable good (Coconut, Rice, Silk, or Gun) within the game, 
 *                       then stores its name so that it can be priced and traded at different ports.
 */
public class Good {
    private String name;

    /**
     * Initializes a new good with the given name.
     *
     * @param name of the good.
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

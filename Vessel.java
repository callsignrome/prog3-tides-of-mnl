import java.util.Map;

public class Vessel {
    private Map<Good, int> cargo;
    public static final int CAPACITY = 75; // turned this public since static final naman, hope thats ok

    public Vessel() {
        cargo = new Map<>();
    }

    // unfinished, not sure kung pano mo siya na-envision so i'll leave this to u nalang
    public int getQuantity(Good good) {
        return;
    }

    public int getTotalCargo() {
        return;
    }

    public int getFreeSpace() {
        return;
    }

    public boolean load(Good good, int qty) {
        return true;
    }

    public boolean unload(Good good, int qty) {
        return true
    }
}
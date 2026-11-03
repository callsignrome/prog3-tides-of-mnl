import java.util.Map;

public class Port {
    private String name;
    private Map<Good, int[]> ranges;
    private Map<Good, int> prices;

    public Port(String name) {
        this.name = name;
        ranges = new Map<>();
        prices = new Map<>();
    }

    public void setRange(Good good, int min, int max) {
        ranges.put(good, new int[] {min, max});
    }

    public void generatePrices() {
        for (Map.Entry<Good, int[]> pair : ranges.entrySet()) {
            Good good = pair.getKey();
            int[] range = pair.getValue();

            int min = range[0];
            int max = range[1];

            int price = min + (int) (Math.random() * ((max - min) + 1));

            prices.put(good, price);
        }
    }

    public int getPrice(Good good) {
        return prices.get(good);
    }
}
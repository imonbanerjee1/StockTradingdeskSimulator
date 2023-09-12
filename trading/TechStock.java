package trading;

import java.util.Random;

public class TechStock extends Stock {
    public TechStock(String name, double open, double high, double low, double close) {
        super(name, open, high, low, close);
    }

    @Override
    public String getStockType() {
        return "Tech";
    }

    @Override
    protected double generateRandomPrice() {
        // Generate a random price change between -1 and 1 for Tech stock
        Random random = new Random();
        double priceChange = (random.nextDouble() * 2) - 1; // Generates a random value between -1 and 1
        double newPrice = getCurrentPrice() + 1.2*priceChange;
        setCurrentPrice(newPrice);
        return newPrice;
    }
}
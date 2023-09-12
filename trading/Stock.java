package trading;

public abstract class Stock {
    private String name;
    private double open;
    private double high;
    private double low;
    private double close;
    private double currentPrice;
    private int numberOfStocks;
    private double totalCashAmount;

    public Stock() {
    }

    public Stock(String name, double open, double high, double low, double close) {
        this.name = name;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.currentPrice = close;
        this.numberOfStocks = 0; // Initialize with 0 stocks
        this.totalCashAmount = 0.0; // Initialize with 0 total cash amount
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getName() {
        return this.name;
    }

    public String getOHLCData() {
        return "Open: " + open + ", High: " + high + ", Low: " + low + ", Close: " + close;
    }

    public int getNumberOfStocks() {
        return numberOfStocks;
    }

    public void setNumberOfStocks(int numberOfStocks) {
        this.numberOfStocks = numberOfStocks;
    }

    public double getTotalCashAmount() {
        return totalCashAmount;
    }

    public void setTotalCashAmount(double totalCashAmount) {
        this.totalCashAmount = totalCashAmount;
    }

    // Abstract method to get the stock type
    public abstract String getStockType();

    // Abstract method to generate a random price
    protected abstract double generateRandomPrice();
}

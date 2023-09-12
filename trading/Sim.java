package trading;

public class Sim {
    public static Stock[] stocks = new Stock[10];
    private static double profit;
    public Sim(){
        stocks[0] = new BankingStock("HDFC", 1625, 1650, 1620, 1630);
        stocks[1] = new FMCGStock("RELIANCE", 2000, 2050, 1950, 2025);
        stocks[2] = new TechStock("TATA", 1300, 1350, 1290, 1315);
        stocks[3] = new PharmaStock("SUNPHARMA", 800, 810, 790, 805);
        stocks[4] = new ManuStock("L&T", 1600, 1630, 1590, 1610);
        stocks[5] = new BankingStock("ICICI", 650, 670, 640, 655);
        stocks[6] = new FMCGStock("ITC", 220, 230, 215, 225);
        stocks[7] = new TechStock("INFY", 1650, 1680, 1635, 1675);
        stocks[8] = new PharmaStock("CIPLA", 700, 710, 690, 705);
        stocks[9] = new ManuStock("TATASTEEL", 1200, 1230, 1190, 1210);
        profit = 0.0;
    }

    public Stock[] getStockData() {
        return stocks;
    }

    public double getProfit() {
        return profit;
    }

    // Setter for profit
    public void setProfit(double newProfit) {
        profit += newProfit;
    }

    public boolean doesStockExist(String stockName, Stock[] stocks) {
        for (Stock stock : stocks) {
            if (stock.getName().equalsIgnoreCase(stockName)) {
                return true; // Found the stock
            }
        }
        return false; // Stock not found
    }


    public void updatePrice(){
        for(Stock stock:stocks){
            stock.generateRandomPrice();
        }
    }
}

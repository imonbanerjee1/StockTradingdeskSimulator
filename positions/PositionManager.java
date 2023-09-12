package positions;

import trading.*;

public class PositionManager {

    public PositionManager() {
    }

    public static double roundtotwo(double num) {
        double ans = Math.round(num * 100.0) / 100.0;
        return ans;
    }

    public String buy(Stock stock, int quantity) {
        int currentNumberOfStocks = stock.getNumberOfStocks();
        double currentTotalCash = stock.getTotalCashAmount();
        int newNumberOfStocks = currentNumberOfStocks + quantity;

        stock.setNumberOfStocks(newNumberOfStocks);
        double cashValue = quantity * stock.getCurrentPrice();
        double newTotalCash = currentTotalCash + cashValue;

        stock.setTotalCashAmount(newTotalCash);

        return "Bought " + quantity + " shares of " + stock.getName() +
                " at ₹" + roundtotwo(stock.getCurrentPrice()) + " each. Total cash value: ₹" + roundtotwo(cashValue);
    }

    public String sell(Stock stock, int quantity, Sim sim) {
        int currentNumberOfStocks = stock.getNumberOfStocks();
        double currentTotalCash = stock.getTotalCashAmount();
        double meanValue = currentTotalCash / currentNumberOfStocks;

        if (currentNumberOfStocks >= quantity) {
            int newNumberOfStocks = currentNumberOfStocks - quantity;
            stock.setNumberOfStocks(newNumberOfStocks);

            double cashValue = quantity * stock.getCurrentPrice();
            double newTotalCash = currentTotalCash - meanValue * quantity;
            stock.setTotalCashAmount(newTotalCash);

            double pnl = quantity * (stock.getCurrentPrice() - meanValue);
            sim.setProfit(pnl);

            String profitOrLoss = (pnl >= 0) ? "Profit" : "Loss";
            double absolutePnl = Math.abs(pnl);

            return "Sold " + quantity + " shares of " + stock.getName() +
                    " at ₹" + roundtotwo(stock.getCurrentPrice()) + " each. Total cash value: ₹" + roundtotwo(cashValue) +
                    "\n" + profitOrLoss + ": ₹" + roundtotwo(absolutePnl);
        } else {
            return "Error: Insufficient shares to sell for " + stock.getName();
        }
    }

    public String displayCurrentPositions(Stock[] stocks, Sim sim) {
        StringBuilder result = new StringBuilder();

        for (Stock stock : stocks) {
            int quantity = stock.getNumberOfStocks();
            double totalCash = stock.getTotalCashAmount();
            if (quantity == 0) {
                continue;
            } else {
                result.append("Stock: ").append(stock.getName()).append(", Quantity: ").append(quantity)
                        .append(", Total Cash Value: ₹").append(roundtotwo(totalCash)).append("\n");
            }
        }
        result.append("\nOverall Profit/Loss: ₹" + roundtotwo(sim.getProfit()));
        return result.toString();
    }
}

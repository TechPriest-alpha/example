package io.example.preparations.easy.arrays;

/**
 * probably idea is to sell just before price goes down and buy on the next day if possible
 */
public class BuyAndSellStockV2 {
    public int maxProfit(int[] prices) {
        return maxProfit2(prices);
    }

    private int maxProfit2(int[] prices) {
        var minPrice = prices[0];
        var profit = 0;
        for (int i = 1; i < prices.length; i++) {
            System.out.println("i= " + i + " minPrice = " + minPrice + " profit = " + profit);
            var nextPrice = i < prices.length-1 ? prices[i+1] : Integer.MIN_VALUE;
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else if (prices[i] > nextPrice) {
                // sell stock
                profit += prices[i] - minPrice;
                minPrice = nextPrice;
            }
        }
        return profit;
    }
}

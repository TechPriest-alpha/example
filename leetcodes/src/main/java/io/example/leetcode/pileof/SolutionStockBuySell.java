package io.example.leetcode.pileof;

public class SolutionStockBuySell {


    public int maxProfit(int[] prices) {
        var min = prices[0];
        var maxDiff = 0;
        for (var i = 1; i < prices.length; i++) {
            if (prices[i] < min) min = prices[i];
            else maxDiff = Math.max(prices[i] - min, maxDiff);
        }
        return maxDiff;
    }
}

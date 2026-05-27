package io.example.preparations.easy.dynamics;

public class BuySellStock {
    public int maxProfit(int[] prices) {
        var min = prices[0];
        var maxDiff = 0;
        for (var i = 1; i < prices.length; i++) {
            if (prices[i] < min) {min = prices[i];} else {maxDiff = Math.max(prices[i] - min, maxDiff);}
        }
        return maxDiff;
    }
}

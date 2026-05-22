package io.example.preparations.easy.arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BuyAndSellStockV2Test {
    @Test
    void case1() {
        var result = new BuyAndSellStockV2().maxProfit(new int[] {7,1,5,3,6,4});
        Assertions.assertThat(result).isEqualTo(7);
    }

    @Test
    void case2() {
        var result = new BuyAndSellStockV2().maxProfit(new int[] {1,2,3,4,5});
        Assertions.assertThat(result).isEqualTo(4);
    }

    @Test
    void case3() {
        var result = new BuyAndSellStockV2().maxProfit(new int[] {7,6,4,3,1});
        Assertions.assertThat(result).isEqualTo(0);
    }
}
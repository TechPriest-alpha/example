package io.example.leetcode.pileof;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionStockBuySellTest {

    @Test
    void case1() {
        var result = new SolutionStockBuySell().maxProfit(new int[] {7,1,5,3,6,4});
        Assertions.assertThat(result).isEqualTo(5);
    }

    @Test
    void case2() {
        var result = new SolutionStockBuySell().maxProfit(new int[] {7,6,4,3,1});
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    void case3() {
        var result = new SolutionStockBuySell().maxProfit(new int[] {1, 1, 1,1});
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    void case4() {
        var result = new SolutionStockBuySell().maxProfit(new int[] {3,2,6,5,0,3});
        Assertions.assertThat(result).isEqualTo(4);
    }

    @Test
    void case5() {
        var result = new SolutionStockBuySell().maxProfit(new int[] {1,2});
        Assertions.assertThat(result).isEqualTo(1);
    }
}
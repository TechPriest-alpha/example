package io.example.leetcode.pileof;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaxSubarrayProductSolutionTest {
    @Test
    void case1() {
        var result = new MaxSubarrayProductSolution().maxProduct(new int[] {2,3,-2,4});
        Assertions.assertThat(result).isEqualTo(6);
    }

    @Test
    void case2() {
        var result = new MaxSubarrayProductSolution().maxProduct(new int[] {-2,0,-1});
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    void case3() {
        var result = new MaxSubarrayProductSolution().maxProduct(new int[] {-3,-1,-1});
        Assertions.assertThat(result).isEqualTo(3);
    }

    @Test
    void case4() {
        var result = new MaxSubarrayProductSolution().maxProduct(new int[] {3,-1,4});
        Assertions.assertThat(result).isEqualTo(4);
    }
}
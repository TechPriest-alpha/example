package io.example.leetcode.pileof;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MaxSubarraySumSolutionTest {
    @Test
    void case1() {
        var result = new MaxSubarraySumSolution().maxSubArray(new int[] {-2,1,-3,4,-1,2,1,-5,4});
        Assertions.assertThat(result).isEqualTo(6);
    }

    @Test
    void case2() {
        var result = new MaxSubarraySumSolution().maxSubArray(new int[] {1});
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void case3() {
        var result = new MaxSubarraySumSolution().maxSubArray(new int[] {5,4,-1,7,8});
        Assertions.assertThat(result).isEqualTo(23);
    }

    @Test
    void case4() {
        var result = new MaxSubarraySumSolution().maxSubArray(new int[] {-1});
        Assertions.assertThat(result).isEqualTo(-1);
    }

    @Test
    void case5() {
        var result = new MaxSubarraySumSolution().maxSubArray(new int[] {-1, -2, -3});
        Assertions.assertThat(result).isEqualTo(-1);
    }
}
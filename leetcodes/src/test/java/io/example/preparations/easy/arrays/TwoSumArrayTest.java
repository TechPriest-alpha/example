package io.example.preparations.easy.arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TwoSumArrayTest {
    @Test
    void case1() {
        var result = new TwoSumArray().twoSum(new int[]{2, 7, 11, 15}, 9);
        Assertions.assertThat(result).containsExactly(0 ,1);
    }

    @Test
    void case2() {
        var result = new TwoSumArray().twoSum(new int[]{3,2,4}, 6);
        Assertions.assertThat(result).containsExactly(1,2);
    }

    @Test
    void case3() {
        var result = new TwoSumArray().twoSum(new int[]{3,3}, 6);
        Assertions.assertThat(result).containsExactly(0,1);
    }
}
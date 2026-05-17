package io.example.leetcode.bits;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionBitsSumTest {
    @Test
    void case1() {
        var result = new SolutionBitsSum().getSum(1, 2);
        Assertions.assertThat(result).isEqualTo(3);
    }

    @Test
    void case2() {
        var result = new SolutionBitsSum().getSum(-1, 2);
        Assertions.assertThat(result).isEqualTo(2);
    }


    @Test
    void case3() {
        var result = new SolutionBitsSum().getSum(3, 2);
        Assertions.assertThat(result).isEqualTo(5);
    }
}
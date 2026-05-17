package io.example.leetcode.pileof;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class SolutionFindMinInRotatedArrayTest {
    @Test
    void case1() {
        var result = new SolutionFindMinInRotatedArray().findMin(new int[] {3,4,5,1,2});
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void case2() {
        var result = new SolutionFindMinInRotatedArray().findMin(new int[] {4,5,6,7,0,1,2});
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    void case3() {
        var result = new SolutionFindMinInRotatedArray().findMin(new int[] {11,13,15,17});
        Assertions.assertThat(result).isEqualTo(11);
    }
}
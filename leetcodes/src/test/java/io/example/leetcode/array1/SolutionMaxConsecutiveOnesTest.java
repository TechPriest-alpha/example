package io.example.leetcode.array1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class SolutionMaxConsecutiveOnesTest {
    @Test
    void case1() {
        var result = new SolutionMaxConsecutiveOnes().findMaxConsecutiveOnes(new int[] {1,1,0,1,1,1});
        Assertions.assertThat(result).isEqualTo(3);
    }

    @Test
    void case2() {
        var result = new SolutionMaxConsecutiveOnes().findMaxConsecutiveOnes(new int[] {1,0,1,1,0,1});
        Assertions.assertThat(result).isEqualTo(2);
    }
}
package io.example.leetcode.stack;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionMaxRectangleTest {

    @Test
    void case1() {
        var result = new SolutionMaxRectangle().largestRectangleArea(new int[] {2,1,5,6,2,3});
        Assertions.assertThat(result).isEqualTo(10);
    }

    @Test
    void case2() {
        var result = new SolutionMaxRectangle().largestRectangleArea(new int[] {2,4});

        Assertions.assertThat(result).isEqualTo(4);
    }
}
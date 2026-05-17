package io.example.leetcode.pileof;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SolutionProductExceptSelfTest {
    @Test
    void case1() {
        var result = new SolutionProductExceptSelf().productExceptSelf(new int[]{1, 2, 3, 4});
        Assertions.assertThat(result).containsExactly(24, 12, 8, 6);
    }

    @Test
    void case2() {
        var result = new SolutionProductExceptSelf().productExceptSelf(new int[]{-1, 1, 0, -3, 3});
        Assertions.assertThat(result).containsExactly(0, 0, 9, 0, 0);
    }

    @Test
    void case3() {
        var result = new SolutionProductExceptSelf().productExceptSelf(new int[]{-1, 1, 0, -3, 3, 0});
        Assertions.assertThat(result).containsExactly(0, 0, 0, 0, 0, 0);
    }
}
package io.example.leetcode.array1;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SolutionArrayDuplicationTest {
    @Test
    public void case1() {
        var result1 = new SolutionArrayDuplication().getConcatenation(new int[]{1, 2, 1});
        Assertions.assertThat(result1).containsExactly(1, 2, 1, 1, 2, 1);
    }

    @Test
    public void case2() {
        var result1 = new SolutionArrayDuplication().getConcatenation(new int[]{1, 3, 2, 1});
        Assertions.assertThat(result1).containsExactly(1, 3, 2, 1, 1, 3, 2, 1);
    }
}
package io.example.leetcode.array2;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionNumbersDisappearedTest {

    @Test
    void case1() {
        var result = new SolutionNumbersDisappeared().findDisappearedNumbers(new int[] {4,3,2,7,8,2,3,1});

        Assertions.assertThat(result).isEqualTo(List.of(5, 6));
    }

    @Test
    void case2() {
        var result = new SolutionNumbersDisappeared().findDisappearedNumbers(new int[] {1, 1});

        Assertions.assertThat(result).isEqualTo(List.of(2));
    }
}
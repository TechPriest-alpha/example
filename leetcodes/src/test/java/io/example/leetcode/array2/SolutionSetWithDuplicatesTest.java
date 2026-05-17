package io.example.leetcode.array2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class SolutionSetWithDuplicatesTest {

    @Test
    void case1() {
        var result = new SolutionSetWithDuplicates().findErrorNums(new int[]{1,1,3,4,5});
        Assertions.assertThat(result).containsExactly(1, 2);
    }

    @Test
    void case2() {
        var result = new SolutionSetWithDuplicates().findErrorNums(new int[]{1,3,3,4,5});
        Assertions.assertThat(result).containsExactly(3, 2);
    }

    @Test
    void case3() {
        var result = new SolutionSetWithDuplicates().findErrorNums(new int[]{1,2,3,4,1});
        Assertions.assertThat(result).containsExactly(1, 5);
    }

    @Test
    void case4() {
        var result = new SolutionSetWithDuplicates().findErrorNums(new int[]{1,2,3,4,2});
        Assertions.assertThat(result).containsExactly(2, 5);
    }

    @Test
    void case5() {
        var result = new SolutionSetWithDuplicates().findErrorNums(new int[]{1,5,3,4,5});
        Assertions.assertThat(result).containsExactly(5, 2);
    }

    @Test
    void case6() {
        var result = new SolutionSetWithDuplicates().findErrorNums(new int[]{1,4,3,4,5});
        Assertions.assertThat(result).containsExactly(4, 2);
    }

    @Test
    void case7() {
        var result = new SolutionSetWithDuplicates().findErrorNums(new int[]{1,2,4,4,5});
        Assertions.assertThat(result).containsExactly(4, 3);
    }


    @Test
    void case8() {
        var result = new SolutionSetWithDuplicates().findErrorNums(new int[]{3,2,2});
        Assertions.assertThat(result).containsExactly(2, 1);
    }

    @Test
    void case9() {
        var result = new SolutionSetWithDuplicates().findErrorNums(new int[]{1,1});
        Assertions.assertThat(result).containsExactly(1, 2);
    }
}
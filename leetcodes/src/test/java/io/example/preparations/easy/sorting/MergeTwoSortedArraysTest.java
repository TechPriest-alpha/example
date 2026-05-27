package io.example.preparations.easy.sorting;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MergeTwoSortedArraysTest {
    @Test
    void case1() {
        var result = new int[]{1, 2, 3, 0, 0, 0};
        new MergeTwoSortedArrays().merge(result, 3, new int[]{2, 5, 6}, 3);
        Assertions.assertThat(result).containsExactly(1, 2, 2, 3, 5, 6);
    }

    @Test
    void case2() {
        var result = new int[]{1};
        new MergeTwoSortedArrays().merge(result, 1, new int[0], 0);
        Assertions.assertThat(result).containsExactly(1);
    }

    @Test
    void case3() {
        var result = new int[]{0};
        new MergeTwoSortedArrays().merge(result, 0, new int[]{1}, 1);
        Assertions.assertThat(result).containsExactly(1);
    }

    @Test
    void case4() {
        var result = new int[]{2, 0};
        new MergeTwoSortedArrays().merge(result, 1, new int[]{1}, 1);
        Assertions.assertThat(result).containsExactly(1, 2);
    }
}
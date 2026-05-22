package io.example.preparations.easy.arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RemoveDuplicatesFromSortedArrayTest {
    @Test
    void case1() {
        var nums = new int[]{1, 1, 2};
        var result = new RemoveDuplicatesFromSortedArray().removeDuplicates(nums);
        Assertions.assertThat(result).isEqualTo(2);
        Assertions.assertThat(nums).startsWith(1, 2);
    }

    @Test
    void case2() {
        var nums = new int[]{0,0,1,1,1,2,2,3,3,4};
        var result = new RemoveDuplicatesFromSortedArray().removeDuplicates(nums);
        Assertions.assertThat(result).isEqualTo(5);
        Assertions.assertThat(nums).startsWith(0,1,2,3,4);
    }
}
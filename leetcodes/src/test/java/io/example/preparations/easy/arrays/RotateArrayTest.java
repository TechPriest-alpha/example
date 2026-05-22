package io.example.preparations.easy.arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RotateArrayTest {
    @Test
    void case1() {
        var nums = new int[]{1, 2, 3, 4, 5, 6, 7};
        new RotateArray().rotate(nums, 3);
        Assertions.assertThat(nums).containsExactly(5, 6, 7, 1, 2, 3, 4);
    }

    @Test
    void case2() {
        var nums = new int[]{-1,-100,3,99};
        new RotateArray().rotate(nums, 2);
        Assertions.assertThat(nums).containsExactly(3,99,-1,-100);
    }

    @Test
    void case3() {
        var nums = new int[]{1,2};
        new RotateArray().rotate(nums, 7);
        Assertions.assertThat(nums).containsExactly(2,1);
    }
}
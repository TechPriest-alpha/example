package io.example.preparations.easy.arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MoveZerosInArrayTest {
    @Test
    void case1() {
        var arr = new int[] {0,1,0,3,12};
        new MoveZerosInArray().moveZeroes(arr);
        Assertions.assertThat(arr).containsExactly(1,3,12,0,0);
    }

    @Test
    void case2() {
        var arr = new int[] {0};
        new MoveZerosInArray().moveZeroes(arr);
        Assertions.assertThat(arr).containsExactly(0);
    }

    @Test
    void case3() {
        var arr = new int[] {1, 0, 0, 2, 0, 3, 0,0,0,4,0};
        new MoveZerosInArray().moveZeroes(arr);
        Assertions.assertThat(arr).containsExactly(1, 2, 3, 4, 0, 0, 0,0,0,0,0);
    }

    @Test
    void case4() {
        var arr = new int[] {0,0,1, 0, 0, 2, 0, 3, 0,0,0,4,0};
        new MoveZerosInArray().moveZeroes(arr);
        Assertions.assertThat(arr).containsExactly(1, 2, 3, 4, 0, 0, 0,0,0,0,0,0,0);
    }
}
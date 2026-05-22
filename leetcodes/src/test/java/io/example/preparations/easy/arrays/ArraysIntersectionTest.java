package io.example.preparations.easy.arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ArraysIntersectionTest {
    @Test
    void case1() {
        var result = new ArraysIntersection().intersect(new int[] {1,2,2,1}, new int[] {2,2});
        Assertions.assertThat(result).containsExactly(2, 2);
    }

    @Test
    void case2() {
        var result = new ArraysIntersection().intersect(new int[] {4,9,5}, new int[] {9,4,9,8,4});
        Assertions.assertThat(result).containsOnly(9, 4);
    }
}
package io.example.preparations.easy.arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LargeIntegerPlusOneTest {
    @Test
    void case1() {
        var result = new LargeIntegerPlusOne().plusOne(new int[]{1,2,3});
        Assertions.assertThat(result).containsExactly(1,2,4);
    }

    @Test
    void case2() {
        var result = new LargeIntegerPlusOne().plusOne(new int[]{4,3,2,1});
        Assertions.assertThat(result).containsExactly(4,3,2,2);
    }

    @Test
    void case3() {
        var result = new LargeIntegerPlusOne().plusOne(new int[]{9});
        Assertions.assertThat(result).containsExactly(1,0);
    }
    @Test
    void case4() {
        var result = new LargeIntegerPlusOne().plusOne(new int[]{8,9,9,9});
        Assertions.assertThat(result).containsExactly(9,0,0,0);
    }
}
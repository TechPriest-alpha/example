package io.example.preparations.easy.arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SingleNumberInArrayTest {
    @Test
    void case1() {
        var result = new SingleNumberInArray().singleNumber(new int[] {2,2,1});
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void case2() {
        var result = new SingleNumberInArray().singleNumber(new int[] {4,1,2,1,2});
        Assertions.assertThat(result).isEqualTo(4);
    }

    @Test
    void case3() {
        var result = new SingleNumberInArray().singleNumber(new int[] {1});
        Assertions.assertThat(result).isEqualTo(1);
    }
}
package io.example.preparations.easy.dynamics;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HouseRobberTest {
    @Test
    void case1() {
        var result = new HouseRobber().rob(new int[]{1, 2, 3, 1});
        Assertions.assertThat(result).isEqualTo(4);
    }

    @Test
    void case2() {
        var result = new HouseRobber().rob(new int[]{2, 7, 9, 3, 1});
        Assertions.assertThat(result).isEqualTo(12);
    }

    @Test
    void case3() {
        var result = new HouseRobber().rob(new int[]{1, 3, 1, 11, 12});
        Assertions.assertThat(result).isEqualTo(15);
    }

    @Test
    void case4() {
        var result = new HouseRobber().rob(new int[]{2, 1, 1, 2});
        Assertions.assertThat(result).isEqualTo(4);
    }

    @Test
    void case5() {
        var result = new HouseRobber().rob(new int[]{2, 1, 1, 1, 1, 2});
        Assertions.assertThat(result).isEqualTo(5);
    }
}
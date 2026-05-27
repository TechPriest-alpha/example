package io.example.preparations.easy.dynamics;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StairClimbTest {
    @Test
    void case1() {
        var result = new StairClimb().climbStairs(2);
        Assertions.assertThat(result).isEqualTo(2);
    }

    @Test
    void case2() {
        var result = new StairClimb().climbStairs(3);
        Assertions.assertThat(result).isEqualTo(3);
    }

    @Test
    void case3() {
        var result = new StairClimb().climbStairs(1);
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void case4() {
        var result = new StairClimb().climbStairs(5);
        Assertions.assertThat(result).isEqualTo(8);
    }

    @Test
    void case5() {
        var result = new StairClimb().climbStairs(44);
        Assertions.assertThat(result).isEqualTo(1134903170);
    }
}
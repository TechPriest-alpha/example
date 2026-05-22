package io.example.preparations.easy.strings;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RotateIntegerTest {
    @Test
    void case1() {
        var result = new RotateInteger().reverse(123);
        Assertions.assertThat(result).isEqualTo(321);
    }

    @Test
    void case2() {
        var result = new RotateInteger().reverse(-123);
        Assertions.assertThat(result).isEqualTo(-321);
    }

    @Test
    void case3() {
        var result = new RotateInteger().reverse(120);
        Assertions.assertThat(result).isEqualTo(21);
    }

    @Test
    void case4() {
        var result = new RotateInteger().reverse(0);
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    void case5() {
        var result = new RotateInteger().reverse(90000);
        Assertions.assertThat(result).isEqualTo(9);
    }

    @Test
    void case6() {
        var result = new RotateInteger().reverse(1);
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void case7() {
        var result = new RotateInteger().reverse(1534236469);
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    void case8() {
        var result = new RotateInteger().reverse(-2147483412);
        Assertions.assertThat(result).isEqualTo(-2143847412);
    }
}
package io.example.preparations.easy.strings;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomAtoiTest {
    @Test
    void case1() {
        var result = new CustomAtoi().myAtoi("42");
        Assertions.assertThat(result).isEqualTo(42);
    }

    @Test
    void case2() {
        var result = new CustomAtoi().myAtoi("-042");
        Assertions.assertThat(result).isEqualTo(-42);
    }

    @Test
    void case3() {
        var result = new CustomAtoi().myAtoi("1337c0d3");
        Assertions.assertThat(result).isEqualTo(1337);
    }

    @Test
    void case4() {
        var result = new CustomAtoi().myAtoi("0-1");
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    void case5() {
        var result = new CustomAtoi().myAtoi("words and 987");
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    void case6() {
        var result = new CustomAtoi().myAtoi("-91283472332");
        Assertions.assertThat(result).isEqualTo(-2147483648);
    }

    @Test
    void case7() {
        var result = new CustomAtoi().myAtoi("+-12");
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    void case9() {
        var result = new CustomAtoi().myAtoi("1");
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void case10() {
        var result = new CustomAtoi().myAtoi("");
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    void case11() {
        var result = new CustomAtoi().myAtoi("  ");
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    void case12() {
        var result = new CustomAtoi().myAtoi("2147483646");
        Assertions.assertThat(result).isEqualTo(2147483646);
    }

    @Test
    void case13() {
        var result = new CustomAtoi().myAtoi("21474836460");
        Assertions.assertThat(result).isEqualTo(2147483647);
    }
}
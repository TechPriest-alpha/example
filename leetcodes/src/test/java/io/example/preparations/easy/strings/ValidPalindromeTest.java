package io.example.preparations.easy.strings;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidPalindromeTest {
    @Test
    void case1() {
        var result = new ValidPalindrome().isPalindrome("A man, a plan, a canal: Panama");
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void case2() {
        var result = new ValidPalindrome().isPalindrome("race a car");
        Assertions.assertThat(result).isFalse();
    }

    @Test
    void case3() {
        var result = new ValidPalindrome().isPalindrome(" ");
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void case4() {
        var result = new ValidPalindrome().isPalindrome("0P");
        Assertions.assertThat(result).isFalse();
    }
}
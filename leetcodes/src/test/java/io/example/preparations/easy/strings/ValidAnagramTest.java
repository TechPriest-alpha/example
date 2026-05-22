package io.example.preparations.easy.strings;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidAnagramTest {
    @Test
    void case1() {
        var result = new ValidAnagram().isAnagram("anagram", "nagaram");
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void case2() {
        var result = new ValidAnagram().isAnagram("rat", "car");
        Assertions.assertThat(result).isFalse();
    }
}
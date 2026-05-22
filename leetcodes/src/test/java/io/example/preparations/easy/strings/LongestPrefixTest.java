package io.example.preparations.easy.strings;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LongestPrefixTest {
    @Test
    void case1() {
        var result = new LongestPrefix().longestCommonPrefix(new String[]{"flower","flow","flight"});
        Assertions.assertThat(result).isEqualTo("fl");
    }

    @Test
    void case2() {
        var result = new LongestPrefix().longestCommonPrefix(new String[]{"dog","racecar","car"});
        Assertions.assertThat(result).isEqualTo("");
    }
}
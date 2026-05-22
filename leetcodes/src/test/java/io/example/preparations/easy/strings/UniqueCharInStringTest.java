package io.example.preparations.easy.strings;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniqueCharInStringTest {
    @Test
    void case1() {
        var result = new UniqueCharInString().firstUniqChar("leetcode");
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    void case2() {
        var result = new UniqueCharInString().firstUniqChar("loveleetcode");
        Assertions.assertThat(result).isEqualTo(2);
    }

    @Test
    void case3() {
        var result = new UniqueCharInString().firstUniqChar("aabb");
        Assertions.assertThat(result).isEqualTo(-1);
    }
}
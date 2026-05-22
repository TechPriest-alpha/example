package io.example.preparations.easy.strings;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RotateStringTest {
    @Test
    void case1() {
        var input = new char[] {'h','e','l','l','o'};
        new RotateString().reverseString(input);
        Assertions.assertThat(input).isEqualTo(new char[] {'o','l','l','e','h'});
    }

    @Test
    void case2() {
        var input = new char[] {'H','a','n','n','a','h'};
        new RotateString().reverseString(input);
        Assertions.assertThat(input).isEqualTo(new char[] {'h','a','n','n','a','H'});
    }
}
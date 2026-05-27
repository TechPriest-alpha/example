package io.example.preparations.easy.sorting;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class FirstBadVersionTest {
    @Test
    void case1() {
        var result = new FirstBadVersion(4).firstBadVersion(5);
        Assertions.assertThat(result).isEqualTo(4);
    }

    @Test
    void case2() {
        var result = new FirstBadVersion(1).firstBadVersion(1);
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void case3() {
        var result = new FirstBadVersion(2).firstBadVersion(2);
        Assertions.assertThat(result).isEqualTo(2);
    }

    @Test
    void case4() {
        var result = new FirstBadVersion(4).firstBadVersion(4);
        Assertions.assertThat(result).isEqualTo(4);
    }

    @Test
    void case5() {
        var result = new FirstBadVersion(1702766719).firstBadVersion(2126753390);
        Assertions.assertThat(result).isEqualTo(1702766719);
    }
}
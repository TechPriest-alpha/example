package io.example.leetcode.array1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionArrayShuffleTest {

    @Test
    void case1() {
        var result = new SolutionArrayShuffle().shuffle(new int[]{1, 2, 3, 4}, 4);
        Assertions.assertThat(result).containsExactly(1, 3, 2, 4);
    }
}
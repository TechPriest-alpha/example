package io.example.preparations.easy.arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RotateImageTest {
    @Test
    void case1() {
        var data = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        new RotateImage().rotate(data);
        Assertions.assertThat(data).isDeepEqualTo(new int[][]{{7, 4, 1}, {8, 5, 2}, {9, 6, 3}});
    }

    @Test
    void case2() {
        var data = new int[][]{{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}};
        new RotateImage().rotate(data);
        Assertions.assertThat(data).isDeepEqualTo(new int[][]{{15, 13, 2, 5}, {14, 3, 4, 1}, {12, 6, 8, 9}, {16, 7, 10, 11}});
    }
}
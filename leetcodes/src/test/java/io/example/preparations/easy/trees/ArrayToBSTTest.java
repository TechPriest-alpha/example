package io.example.preparations.easy.trees;

import org.junit.jupiter.api.Test;

class ArrayToBSTTest {
    @Test
    void case1() {
        var result = new ArrayToBST().sortedArrayToBST(new int[]{-10, -3, 0, 5, 9});
        System.out.println(result);
    }
}
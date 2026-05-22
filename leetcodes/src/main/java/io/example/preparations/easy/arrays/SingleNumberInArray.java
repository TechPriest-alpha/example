package io.example.preparations.easy.arrays;

/**
 * In the end it was about XOR.
 * That x ^ x = 0, so when we have x, y, z, z, x and XOR all of them, duplicates compensate each other and only single value remains.
 */
public class SingleNumberInArray {
    public int singleNumber(int[] nums) {
        var result = nums[0];
        for (var num : nums) {
            result ^= num;
        }
        return result;
    }
}

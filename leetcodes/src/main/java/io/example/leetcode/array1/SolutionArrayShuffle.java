package io.example.leetcode.array1;

/**
 * Given the array nums consisting of 2n elements in the form [x1,x2,...,xn,y1,y2,...,yn].
 * Return the array in the form [x1,y1,x2,y2,...,xn,yn].
 */
public class SolutionArrayShuffle {
    public int[] shuffle(int[] nums, int n) {
        var l = nums.length;
        var k = l / 2;
        var result = new int[l];
        for (int i = 0; i < l; i+=2) {
            result[i] = nums[i/2];
        }
        for (int i = 1; i < l; i+=2) {
            result[i] = nums[k + (i-1)/2];
        }
        return result;
    }
}

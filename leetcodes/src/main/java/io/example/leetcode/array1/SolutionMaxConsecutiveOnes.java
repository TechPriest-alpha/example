package io.example.leetcode.array1;

import java.util.Arrays;

/**
 * Given a binary array nums, return the maximum number of consecutive 1's in the array.
 */
public class SolutionMaxConsecutiveOnes {

    public int findMaxConsecutiveOnes(int[] nums) {
        var r = 0;
        var max = 0;
        for (int num : nums) {
            if (num == 1) {
                r++;
            } else {
                max = Math.max(max, r);
                r = 0;
            }
        }
        max = Math.max(max, r);

        return max;
    }
}

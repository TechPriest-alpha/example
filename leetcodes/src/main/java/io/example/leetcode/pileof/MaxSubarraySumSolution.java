package io.example.leetcode.pileof;

import java.util.Arrays;

public class MaxSubarraySumSolution {

    public int maxSubArray(int[] nums) {
        var tmp = new int[nums.length + 1];
//        Arrays.fill(tmp, -10_001);
//        tmp[0] = 0;
        for (int i = 1; i < nums.length + 1; i++) {
            tmp[i] = Math.max(tmp[i], tmp[i - 1] + nums[i - 1]);
        }

        var max = Arrays.stream(tmp, 1, tmp.length).max().orElseThrow();
//        return max == 0 ? Arrays.stream(nums).max().getAsInt() : max;
        return maxSubArray2(nums);
    }

    public int maxSubArray2(int[] nums) {
        var maxSum = Integer.MIN_VALUE;
        var sum = 0;
//        Arrays.fill(tmp, -10_001);
//        tmp[0] = 0;
        for (int num : nums) {
            sum += num;
            if (maxSum < sum) {maxSum = sum;}
            if (sum < 0) {sum = 0;}
        }

        return maxSum;
    }
}

package io.example.preparations.easy.dynamics;

public class MaxSubarray {
    public int maxSubArray(int[] nums) {

        var maxSum = Integer.MIN_VALUE;
        var sum = 0;

        for (int num : nums) {
            sum += num;
            if (maxSum < sum) {maxSum = sum;}
            if (sum < 0) {sum = 0;}
        }

        return maxSum;

    }
}

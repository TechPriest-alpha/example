package io.example.preparations.easy.dynamics;

import java.util.Arrays;

public class HouseRobber {
    public int rob(int[] nums) {
        return v3(nums);
    }

    private int v3(int[] nums) {
        if (nums.length == 0) {return 0;}
        int prev1 = 0;
        int prev2 = 0;
        for (int num : nums) {
            int tmp = prev1;
            prev1 = Math.max(prev2 + num, prev1);
            prev2 = tmp;
        }
        return prev1;
    }

    private int v2(int[] nums) {
        if (nums.length == 0) {return 0;}
        int[] memo = new int[nums.length + 1];
        memo[0] = 0;
        memo[1] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int val = nums[i];
            memo[i + 1] = Math.max(memo[i], memo[i - 1] + val);
        }
        return memo[nums.length];
    }

    private static int v1(int[] nums) {
        if (nums.length == 0) {
            return 0;
        } else if (nums.length == 1) {
            return nums[0];
        } else {
            var prev = new int[nums.length];
            prev[0] = nums[0];
            prev[1] = nums[1];
            for (int i = 2; i < nums.length; i++) {
                prev[i] = Math.max(prev[i - 1], prev[i - 2] + nums[i]);
            }
            return Arrays.stream(prev).max().getAsInt();
        }
    }
}

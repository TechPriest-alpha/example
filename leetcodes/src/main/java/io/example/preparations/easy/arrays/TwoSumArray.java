package io.example.preparations.easy.arrays;

import java.util.HashMap;
import java.util.Map;

public class TwoSumArray {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            var value = target - nums[i];
            if (map.containsKey(value)) {
                return new int[]{map.get(value), i};
            } else {
                map.put(nums[i], i);
            }
        }
        return null;
    }
}

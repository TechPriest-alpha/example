package io.example.preparations.easy.arrays;

import java.util.Arrays;

/**
 * idea is to run two indexes and replace duplicates with next non-duplicate
 */
public class RemoveDuplicatesFromSortedArray {
    public int removeDuplicates(int[] nums) {
        return solutionV2(nums);
    }

    private int solutionV2(int[] nums) {
        int i = 1, j = 1;
        while (i < nums.length && j < nums.length) {
            System.out.println("i=" + i + " j=" + j);
            if (nums[i-1] == nums[j]) {

            } else {
                nums[i] = nums[j];
                i++;
            }
            j++;
        }
        return i;
    }

    private int solutionV1(int[] nums) {
        int i = 1, j = 1;
        int[] result = new int[nums.length];
        result[0] = nums[0];
        while (i < nums.length && j < nums.length) {
            System.out.println("i=" + i + " j=" + j);
            if (result[i-1] == nums[j]) {

            } else {
                result[i] = nums[j];
                i++;
            }
            j++;
        }
        Arrays.setAll(nums, k -> result[k]);
        return i;
    }
}

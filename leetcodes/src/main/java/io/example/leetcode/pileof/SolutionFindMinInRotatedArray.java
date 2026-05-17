package io.example.leetcode.pileof;

public class SolutionFindMinInRotatedArray {
    public int findMin(int[] nums) {
        return search(nums, 0, nums.length - 1);
    }

    public int search(int[] nums, int start, int end) {
        if (end <= start || nums[end] >= nums[start]) {
            return nums[start];
        } else {
            var middle = (end + start) / 2;
            return nums[middle] < nums[end] ? search(nums, start, middle) : search(nums, middle + 1, end);
        }
    }
}

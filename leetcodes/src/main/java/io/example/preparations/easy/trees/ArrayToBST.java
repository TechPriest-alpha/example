package io.example.preparations.easy.trees;

import java.util.Arrays;

public class ArrayToBST {
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {return null;}
        var middle = nums.length / 2;
        var root = new TreeNode(nums[middle]);
        root.left = sortedArrayToBST(Arrays.copyOfRange(nums, 0, middle));
        root.right = sortedArrayToBST(Arrays.copyOfRange(nums, middle + 1, nums.length));
        return root;
    }
}

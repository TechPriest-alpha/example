package io.example.preparations.easy.arrays;

/**
 * plain and simple - find out how many elements are to be moved to the start of the array.
 * and then
 */
public class RotateArray {
    public void rotate(int[] nums, int k) {
        var realRotations = k % nums.length;
        var tailLength = nums.length - realRotations;
        var original = nums.clone();
        System.arraycopy(original, tailLength, nums, 0, realRotations);
        System.arraycopy(original, 0, nums, realRotations, nums.length - realRotations);
    }
}

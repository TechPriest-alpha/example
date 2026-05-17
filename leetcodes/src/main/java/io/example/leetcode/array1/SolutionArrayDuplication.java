package io.example.leetcode.array1;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

/**
 * Given an integer array nums of length n, you want to create an array ans of length 2n where ans[i] == nums[i] and ans[i + n] == nums[i] for 0 <= i < n (0-indexed).
 * Specifically, ans is the concatenation of two nums arrays.
 * Return the array ans.
 */
@Slf4j
public class SolutionArrayDuplication {
    /**
     * Approach with {@link System#arraycopy} seems to be most prudent and efficient.
     * It is less clear to read and understand though.
     * Leaving manual coping commented out just as history reference.
     * @param nums source array
     * @return result filled with copies of the original
     */
    public int[] getConcatenation(int[] nums) {
        var originalLength = nums.length;
        var l = originalLength * 2;
        ByteBuffer.allocate(1);
        var result = Arrays.copyOf(nums, l);
//        for (int i = originalLength; i < l; i++) {
//            result[i] = nums[i - originalLength];
//        }
//        log.debug("Result 1 = {}", Arrays.stream(result).mapToObj(i -> (Integer) i).toList());
//        Arrays.fill(result, 0);
        System.arraycopy(nums, 0, result, originalLength, originalLength);
        log.debug("Result 2 = {}", Arrays.stream(result).mapToObj(i -> (Integer) i).toList());
        return result;
    }
}

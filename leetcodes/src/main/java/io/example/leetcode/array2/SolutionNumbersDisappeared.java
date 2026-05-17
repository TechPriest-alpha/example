package io.example.leetcode.array2;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Given an array nums of n integers where nums[i] is in the range [1, n], return an array of all the integers in the range [1, n] that do not appear in nums.
 */
public class SolutionNumbersDisappeared {

    public List<Integer> findDisappearedNumbers(int[] nums) {
        var all = IntStream.range(1, nums.length + 1).boxed().collect(Collectors.toSet());
        var source = Arrays.stream(nums).boxed().collect(Collectors.toSet());
        all.removeAll(source);
        return all.stream().toList();
    }
}

package io.example.leetcode.pileof;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MaxSubarrayProductSolution {
    public int maxProduct(int[] nums) {
        var r1= findProduct1(nums, IntStream.range(0, nums.length).iterator());
        var r2 = findProduct1(nums, IntStream.range(0, nums.length).boxed().sorted(Comparator.reverseOrder()).iterator());
        return Math.max(r1, r2);
    }

    private static int findProduct1(int[] nums, Iterator<Integer> iterationOrder) {
        var maxProduct = Integer.MIN_VALUE;
        var product = 1;
        while (iterationOrder.hasNext()) {
            product *= nums[iterationOrder.next()];
            if (maxProduct < product) {maxProduct = product;}
            if (product == 0) {product = 1;}
        }

        return maxProduct;
    }

    private static int findProduct2(int[] nums) {
        var maxProduct = Integer.MIN_VALUE;
        var product = 1;
        for (int i = nums.length - 1; i >=0; i--) {
            product *= nums[i];
            if (maxProduct < product) {maxProduct = product;}
            if (product == 0) {product = 1;}
        }

        return maxProduct;
    }
}

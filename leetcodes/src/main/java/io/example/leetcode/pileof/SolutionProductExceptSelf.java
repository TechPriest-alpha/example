package io.example.leetcode.pileof;

import java.util.Arrays;

public class SolutionProductExceptSelf {
    /**
     * Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].
     * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
     * You must write an algorithm that runs in O(n) time and without using the division operation.
     *
     * @param nums
     * @return
     */
    public int[] productExceptSelf(int[] nums) {
        var result = new int[nums.length];
        var p = new int[2];
        var multiplication = 1;
        for (var i = 0; i < nums.length; i++) {
            if (nums[i] == 0 && p[0] == 0) {p[0] = i + 1;} else if (nums[i] == 0 && p[1] == 0) {p[1] = i + 1;} else {multiplication *= nums[i];}
            if (p[0] != 0 && p[1] != 0) {return result;}
        }

        if (p[0] != 0) {
            result[p[0] - 1] = multiplication;
        } else if (p[1] != 0) {
            result[p[1] - 1] = multiplication;
        } else {
            Arrays.fill(result, 1);
            for (var i = 1; i < nums.length; i++) {
                result[i] = result[i - 1] * nums[i - 1];
            }
//            System.out.println(Arrays.stream(result).boxed().toList() + " r");
            //1,1,2,6
            //24,12,4,1
            var r2 = new int[nums.length];
            Arrays.fill(r2, 1);
            for (var i = nums.length-2; i >=0; i--) {
                r2[i] = r2[i + 1] * nums[i + 1];
            }
            for (int i = 0; i < nums.length; i++) {
                result[i] = result[i] * r2[i];
            }
//            System.out.println(Arrays.stream(r2).boxed().toList() + " r");
        }
        return result;
    }

}

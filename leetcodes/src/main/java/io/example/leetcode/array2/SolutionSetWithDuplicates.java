package io.example.leetcode.array2;

import java.util.Arrays;

/**
 * You have a set of integers s, which originally contains all the numbers from 1 to n. Unfortunately, due to some error, one of the numbers in s got duplicated to another number in the set, which results in repetition of one number and loss of another number.
 * You are given an integer array nums representing the data status of this set after the error.
 * Find the number that occurs twice and the number that is missing and return them in the form of an array.
 */
public class SolutionSetWithDuplicates {
    public int[] findErrorNums(int[] nums) {
        var l = nums.length;
        var sum = ((1 + l) * l) / 2;
//        var wrongsum = Arrays.stream(nums).sum();
//        var diff = sum - wrongsum;
        Arrays.sort(nums);
        //1,2,3,4,5
        //1,2,3,4,1 -> 1,1,2,3,4 -> (1, 5) ... 1 != 2, diff = 1 - 2 = -1
        //1,3,3,4,5 -> (3, 2) ... 3 != 2, diff = 3 - 2 = 1
        //
        //если задублировалось число меньшее, чем замещённое, то после сортировки будут два одинаковых числа, одно из которых меньше, чем ожидаемое на позиции
        //т.е. разница с ожиданием будет отрицательной
        //если задублировалось число большее, чем замещённое, что после сортировки будут два одинаковых числа, одно из которых больше, чем ожидаемое на позиции
        //мы знаем задублированное число и ожидаемую сумму
        //если замещено большее число, то сумма будет меньше и замещённое число = дубликат + разница
        //если замещено меньшее число, то сумма будет больше и замещённое число = дубликат - разница по модулю

        int missingNumber = 0;
        int duplicatedNumber = 0;
        int newsum = 0;
        for (int i = 0; i < l; i++) {
            var num = nums[i];
            if (duplicatedNumber == 0) {
                var nextNum = i < l-1 ? nums[i+1] : 0;
                var prevNum = i > 0 ? nums[i-1] : 0;
                if ((nextNum == num || prevNum == num)) {
                    duplicatedNumber = num;
                }
            }
            newsum += num;
        }
        missingNumber = duplicatedNumber + (sum - newsum);
        return new int[] {duplicatedNumber, missingNumber};
    }
}

package io.example.preparations.easy.arrays;

public class MoveZerosInArray {
    public void moveZeroes(int[] nums) {
        elegant(nums);
    }

    private static void elegant(int[] nums) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[k++] = nums[i];
            }
        }
        for (int i = k; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    private static void naive(int[] nums) {
        int pZero = 0, pNonZero = 0;
        do {
//            System.out.println("A pzero: " + pZero + ", pnonzero: " + pNonZero);
            while(pZero < nums.length && nums[pZero] != 0) pZero++;
            while(pNonZero < nums.length && nums[pNonZero] == 0 || pNonZero<pZero) pNonZero++;
//            System.out.println("B pzero: " + pZero + ", pnonzero: " + pNonZero);
            if (pZero < nums.length && pNonZero < nums.length && pZero < pNonZero) {
                nums[pZero] = nums[pNonZero];
                nums[pNonZero] = 0;
                pNonZero++;
                pZero++;
            } else { pNonZero++;}
        } while(pZero < nums.length && pNonZero < nums.length);
    }
}

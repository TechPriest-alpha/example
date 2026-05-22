package io.example.preparations.easy.arrays;

import java.util.ArrayList;
import java.util.Arrays;

public class ArraysIntersection {

    public int[] intersect(int[] nums1, int[] nums2) {
        return intersectAlternate(nums1, nums2);

    }
    public int[] intersectV1(int[] nums1, int[] nums2) {
        var result = new ArrayList<Integer>();
        for (int n : nums1) {
            for (int i = 0; i < nums2.length; i++) {

                if (n == nums2[i]) {
                    result.add(n);
                    nums2[i] = -1;
                    break;
                }
            }
        }
        return result.stream().mapToInt(n -> n).toArray();
    }

    public int[] intersectAlternate(int[] nums1, int[] nums2) {
        var counts = new int[1001];
        if (nums1.length > nums2.length) {
            return doIntersect(nums1, counts, nums2);
        } else {
            return doIntersect(nums2, counts, nums1);
        }
    }

    private static int[] doIntersect(int[] bigArray, int[] counts, int[] smallArray) {
        int[] result;
        for (var n : bigArray) {counts[n]++;}
        result = new int[smallArray.length];
        var k = 0;
        for (var n : smallArray) {
            if (counts[n] > 0) {
                counts[n]--;
                result[k++] = n;
            }
        }
        return Arrays.copyOfRange(result, 0, k);
    }
}

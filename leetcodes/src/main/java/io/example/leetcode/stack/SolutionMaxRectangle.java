package io.example.leetcode.stack;

/**
 * Given an array of integers heights representing the histogram's bar height where the width of each bar is 1, return the area of the largest rectangle in the histogram.
 */
public class SolutionMaxRectangle {
    public int largestRectangleArea(int[] heights) {
        int max = 0;
        for (int i = 0; i < heights.length - 1; i++) {
            var h1 = heights[i];
            var h2 = heights[i+1];
            var minH = Math.min(h1, h2);
            var s = 2 * minH;
            max = Math.max(max, Math.max(s, Math.max(h1, h2)));
        }
        return max;
    }
}

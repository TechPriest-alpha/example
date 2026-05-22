package io.example.preparations.easy.strings;

public class LongestPrefix {
    public String longestCommonPrefix(String[] strs) {
        var start = strs[0];
        int result = start.length();
        for (int i = 1; i < strs.length; i++) {
            var l = 0;
            var str = strs[i];
            var min = Math.min(str.length(), start.length());
            while (l < min && start.charAt(l) == strs[i].charAt(l)) l++;
            start = strs[i];
            result = Math.min(l, result);
        }
        return start.substring(0, result);
    }
}

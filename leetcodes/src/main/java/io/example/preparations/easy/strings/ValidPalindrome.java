package io.example.preparations.easy.strings;

public class ValidPalindrome {
    public boolean isPalindrome(String s) {
        int k = 0;
        var chars = s.toLowerCase().toCharArray();
        var lowerBoundA = 'a';
        var upperBoundB = 'z';
        var lowerBound0 = '0';
        var upperBound9 = '9';
        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] >= lowerBoundA && chars[i] <= upperBoundB) || (chars[i] >= lowerBound0 && chars[i] <= upperBound9)) {
                chars[k++] = chars[i];
            }
        }
        for (int i = 0; i < k / 2; i++) {
            if (chars[i] != chars[k - 1 - i]) return false;
        }
        return true;
    }
}

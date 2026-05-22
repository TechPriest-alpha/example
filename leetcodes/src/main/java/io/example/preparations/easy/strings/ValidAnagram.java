package io.example.preparations.easy.strings;

import java.util.Arrays;

public class ValidAnagram {
    public boolean isAnagram(String s, String t) {
        return extractedV2(s, t);
    }

    private boolean extractedV2(String s, String t) {
        int[] counts = new int[26];
        var shift = 'a';
        var charsS = s.toCharArray();
        var charsT = t.toCharArray();
        for (int i = 0; i < charsS.length; i++) {
            counts[charsS[i] - shift]++;
            counts[charsT[i] - shift]--;
        }
        for (var c: counts) {
            if (c != 0) return false;
        }
        return true;
    }

    private static boolean extractedV1(String s, String t) {
        if (s.length() != t.length())
            return false;
        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();
        Arrays.sort(sArray);
        Arrays.sort(tArray);
        return Arrays.equals(sArray, tArray);
    }
}

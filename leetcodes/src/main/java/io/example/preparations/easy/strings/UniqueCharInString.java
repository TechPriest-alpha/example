package io.example.preparations.easy.strings;

import java.util.HashMap;

public class UniqueCharInString {
    public int firstUniqChar(String s) {
        return extractedV2(s);
    }

    private int extractedV2(String s) {
        int[] counts = new int[26];
        var shift = 'a';
        var chars = s.toCharArray();
        for (var c: chars) {
            counts[c - shift]++;
        }
        for (int i = 0; i < chars.length; i++) {
            if (counts[chars[i] - shift] == 1) return i;
        }
        return -1;
    }

    private static int extractedV1(String s) {
        var chars = s.toCharArray();
        var track = new HashMap<Character, Integer>();
        for (int i = 0; i < chars.length; i++) {
            if (!track.containsKey(chars[i])) {
                track.put(chars[i], i);
            } else {
                track.put(chars[i], -1);
            }
        }
        return track.values().stream().mapToInt(i -> i).filter(i -> i != -1).min().orElse(-1);
    }
}

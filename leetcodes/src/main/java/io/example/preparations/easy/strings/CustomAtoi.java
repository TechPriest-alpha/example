package io.example.preparations.easy.strings;

import java.util.HashMap;
import java.util.Map;

public class CustomAtoi {
    private static final Map<Character, Integer> map = new HashMap<>();

    static {
        map.put('0', 0);
        map.put('1', 1);
        map.put('2', 2);
        map.put('3', 3);
        map.put('4', 4);
        map.put('5', 5);
        map.put('6', 6);
        map.put('7', 7);
        map.put('8', 8);
        map.put('9', 9);
    }

    public int myAtoi(String s) {
        if (s.length() == 1) {return map.getOrDefault(s.charAt(0), 0);}
        var chars = s.toCharArray();
        Integer signum = null;
        int k = 0;
        while (k < chars.length && chars[k] == ' ') {k++;}
        if (k == chars.length) {return 0;}
        if (chars[k] == '+') {
            signum = 1;
            k++;
        } else if (chars[k] == '-') {
            signum = -1;
            k++;
        } else if (!map.containsKey(chars[k])) {
            return 0;
        }
        long result = 0L;
        signum = signum == null ? 1 : signum;

        while (k < chars.length && map.containsKey(chars[k])) {
            var nextNum = map.get(chars[k]);
            result = result * 10 + nextNum;
            if (signum * result <= Integer.MIN_VALUE  ) {
                return Integer.MIN_VALUE;
            } else if (signum * result >= Integer.MAX_VALUE ) {
                return Integer.MAX_VALUE;
            }
            k++;
        }
        return (int) (signum * result);
    }
}

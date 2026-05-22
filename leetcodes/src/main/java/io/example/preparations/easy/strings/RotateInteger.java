package io.example.preparations.easy.strings;

import java.util.Arrays;

public class RotateInteger {
    public int reverse(int x) {
        return reverseV2(x);
    }

    private int reverseV2(int x) {
        //var signum = Math.signum(x);
        int result = 0;
        do {
            var digit = x % 10;
            x /= 10;
            if (result > Integer.MAX_VALUE / 10 || result < Integer.MIN_VALUE / 10) return 0;
            result = result * 10 + digit; //nice trick to avoid manual calculation of number of digits
        } while (x != 0);
        return result;
    }

    private int reverseV1(int x) {
        var asString = x + "";
        var chars = asString.toCharArray();
        reverseString(chars);
        int k = 0;
        while (k < chars.length && chars[k] == '0') k++;
        var signum = chars[chars.length - 1] == '-' ? '-' : '+';
        char[] newChars;
        if (signum == '-') {
            newChars = Arrays.copyOfRange(chars, k, chars.length - 1);
            return newChars.length > 0 ? getAnInt("-" + new String(newChars)) : 0;
        } else {
            newChars = Arrays.copyOfRange(chars, k, chars.length);
            return newChars.length > 0 ? getAnInt(new String(newChars)) : 0;
        }
    }

    private static int getAnInt(String newChars) {
        try {
            return Integer.parseInt(newChars);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void reverseString(char[] s) {
        int len = s.length;
        int i = 0, n = len - 1;
        while (i < n) {
            var tmp = s[i];
            s[i] = s[n];
            s[n] = tmp;
            i++;
            n--;
        }
    }
}

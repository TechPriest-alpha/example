package io.example.preparations.easy.strings;

public class RotateString {
    public void reverseString(char[] s) {
        int len = s.length;
        int i = 0, n = len-1;
        while (i < n) {
            var tmp = s[i];
            s[i] = s[n];
            s[n] = tmp;
            i++;
            n--;
        }
    }
}

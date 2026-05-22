package io.example.preparations.easy.arrays;

public class LargeIntegerPlusOne {
    public int[] plusOne(int[] digits) {
        var hasAddendum = digits[digits.length - 1] == 9;

        if (hasAddendum) {
            for (int i = digits.length - 1; i >= 0; i--) {
                if (digits[i] == 9) {
                    digits[i] = 0;
                } else {
                    digits[i] = digits[i] + 1;
                    hasAddendum = false;
                    break;
                }
            }
            if (hasAddendum) {
                var result = new int[digits.length + 1];
                result[0] = 1;
//                System.arraycopy(digits, 0, result, 1, digits.length);
                return result;
            } else return digits;

        } else {
            digits[digits.length - 1]++;
            return digits;
        }
    }
}

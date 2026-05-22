package io.example.preparations.easy.arrays;

import java.util.Arrays;

public class ValidSudoku {
    public boolean isValidSudoku(char[][] board) {
        var cols = new int[9][9];
        int j = 0;
        var result = new int[10];
        for (var row: board) {
            Arrays.fill(result, 0);
            int i = 0;
            for (char c : row) {
                var value = decodeChar(c);
                cols[i++][j] = value;
                if (value != 0 && result[value] != 0) return false;
                result[value] = 1;
            }
            j++;
        }
        for (var col: cols) {
            Arrays.fill(result, 0);
            for (var value: col) {
                if (value != 0 && result[value] != 0) return false;
                result[value] = 1;
            }
        }
        for (int i = 0; i < 9; i+=3) {
            for (int k = 0; k < 9; k+=3) {
                Arrays.fill(result, 0);

                for (int l = i; l < i+3; l++) {
                    for (int m = k; m < k+3; m++) {
                        var value = cols[l][m];
                        System.out.print(value + " ");
                        if (value != 0 && result[value] != 0) return false;
                        result[value] = 1;
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
        return true;
    }

    public int decodeChar(char ch) {
        return switch (ch) {
            case '1' -> 1;
            case '2' -> 2;
            case '3' -> 3;
            case '4' -> 4;
            case '5' -> 5;
            case '6' -> 6;
            case '7' -> 7;
            case '8' -> 8;
            case '9' -> 9;
            default -> 0;
        };
    }
}

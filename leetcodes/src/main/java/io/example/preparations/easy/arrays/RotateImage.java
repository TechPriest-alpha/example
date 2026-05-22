package io.example.preparations.easy.arrays;

//matrix transposition
// one has to know that rotation = transposition + reverse of each row/column
public class RotateImage {
    public void rotate(int[][] matrix) {
        var n = matrix.length;
        //transpose
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                var temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        //reverse
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                var temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - j - 1];
                matrix[i][n - j - 1] = temp;
            }
        }
    }
}

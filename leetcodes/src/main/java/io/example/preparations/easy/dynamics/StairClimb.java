package io.example.preparations.easy.dynamics;

public class StairClimb {
    public int climbStairs(int n) {
        return solveV2(n);
    }

    //they say 'its Fibonacci', well, whatever
    private int solveV2(int n) {
        if (n == 1) {return 1;}
        if (n == 2) {return 2;}

        int f1 = 1;
        int f2 = 2;
        int result = 0;

        for (int i = 3; i <= n; i++) {
            var fNext = f1 + f2;
            f1 = f2;
            f2 = fNext;
            if (i == n) {result = f2;}
        }
        return result;
    }

    private int solveV1(int n) {
        if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        } else {
            return climbStairs(n - 1) + climbStairs(n - 2);
        }
    }
}

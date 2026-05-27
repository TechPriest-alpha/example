package io.example.preparations.easy.sorting;

public class FirstBadVersion {
    public int badVersion = 3;

    public FirstBadVersion(int badVersion) {
        this.badVersion = badVersion;
    }

    public int firstBadVersion(int n) {
        long middle = n / 2 + n % 2;
        long base = 1;
        do {
            if (isBadVersion((int) middle)) {
                if (!isBadVersion((int) middle - 1)) {
                    return (int) middle;
                } else {
                    middle = base / 2 + middle / 2;// + middle % 2;
                }
            } else {
                base = middle;
//                if (!isBadVersion(middle - 1)) {
//                    return middle;
//                } else {
                middle = base + (n - middle) / 2 + (n - middle) % 2;
//                }
            }
        } while (true);
    }


    public boolean isBadVersion(int version) {
        return version >= badVersion;
    }
}

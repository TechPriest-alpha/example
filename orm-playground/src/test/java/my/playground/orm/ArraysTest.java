package my.playground.orm;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArraysTest {
    @ParameterizedTest
    @MethodSource("params")
    void test(final int[] arr1, final int[] arr2, final int[] arr3, final Integer expected) {
        final var result1 = findIntersectionSimple(arr1, arr2, arr3);
        final var result2 = findIntersectionAdvanced(arr1, arr2, arr3);
        assertEquals(expected, result1);
        assertEquals(expected, result2);
    }

    private Integer findIntersectionAdvanced(final int[] arr1, final int[] arr2, final int[] arr3) {
        final var l1 = arr1.length;
        final var l2 = arr2.length;
        final var l3 = arr3.length;
        if (l1 < 1 || l2 < 1 || l3 < 1) return null;
        final var maxLength = Math.max(l1, Math.max(l2, l3));
        final var counter = new HashMap<Integer, Integer>();

        counter.put(arr1[0], 1);
        counter.compute(arr2[0], (key, value) -> value == null ? 1 : value + 1);
        counter.compute(arr3[0], (key, value) -> value == null ? 1 : value + 1);
        if (counter.get(arr1[0]) == 3) return arr1[0];

        for (int i = 1; i < maxLength; i++) {
            int count = 0;
            if (i < l1 && arr1[i] != arr1[i - 1])
                count = counter.compute(arr1[i], (key, value) -> value == null ? 1 : value + 1);
            if (count >= 3) return arr1[i];
            if (i < l2 && arr2[i] != arr2[i - 1])
                count = counter.compute(arr2[i], (key, value) -> value == null ? 1 : value + 1);
            if (count >= 3) return arr2[i];
            if (i < l3 && arr3[i] != arr3[i - 1])
                count = counter.compute(arr3[i], (key, value) -> value == null ? 1 : value + 1);
            if (count >= 3) return arr3[i];
        }
        return null;
    }

    private Integer findIntersectionSimple(final int[] arr1, final int[] arr2, final int[] arr3) {
        final var l1 = arr1.length;
        final var l2 = arr2.length;
        final var l3 = arr3.length;
//        Integer result = null;
//        var start = Math.max(arr1[0], Math.max(arr2[0], arr3[0]));
        for (final int i : arr1) {
            for (final int j : arr2) {
                for (final int k : arr3) {
                    if (i == j && j == k) return k;
                }
            }
        }
        return null;
    }

    public static Stream<Arguments> params() {
        return Stream.of(
            Arguments.of(new int[]{1, 2, 4, 5}, new int[]{3, 3, 4}, new int[]{2, 3, 4, 5, 6}, 4),
            Arguments.of(new Random().ints(500, 0, 100).sorted().toArray(), new Random().ints(500, 0, 100).sorted().toArray(), new Random().ints(500, 0, 100).sorted().toArray(), 0),
            Arguments.of(new int[]{1, 2, 4, 5}, new int[]{2, 3, 3, 4}, new int[]{2, 3, 4, 5, 6}, 2),
            Arguments.of(new int[]{1}, new int[]{2}, new int[]{3}, null),
            Arguments.of(new int[]{1, 1, 2, 2, 3, 3}, new int[]{3, 3, 4, 4}, new int[]{3}, 3),
            Arguments.of(new int[]{1, 1, 2, 2, 3, 3}, new int[]{3, 3, 4, 4}, new int[]{2, 2, 2, 3}, 3),
            Arguments.of(new int[]{1}, new int[]{1}, new int[]{}, null),
            Arguments.of(new int[]{}, new int[]{}, new int[]{}, null),
            Arguments.of(new int[]{-1, 0, 1}, new int[]{-2, 0, 2}, new int[]{-3, 0, 3}, 0),
            Arguments.of(new int[]{1}, new int[]{1}, new int[]{1}, 1),
            Arguments.of(new int[]{-1}, new int[]{-1}, new int[]{-1}, -1)
        );
    }
}

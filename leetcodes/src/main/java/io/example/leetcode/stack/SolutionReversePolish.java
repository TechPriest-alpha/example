package io.example.leetcode.stack;

import java.util.Set;
import java.util.Stack;

public class SolutionReversePolish {
    private static final Set<String> OPS = Set.of("+", "-", "/", "*");
    public int evalRPN(String[] tokens) {
        var s = new Stack<Integer>();
        for (var t: tokens) {
            if (OPS.contains(t)) {
                var arg2 = s.pop();
                var arg1 = s.pop();
                var r = switch (t) {
                    case "+" -> arg1 + arg2;
                    case "-" -> arg1 - arg2;
                    case "*" -> arg1 * arg2;
                    case "/" -> arg1 / arg2;
                    default -> throw new IllegalArgumentException("illegal");
                };
                s.push(r);
            } else {
                s.push(Integer.valueOf(t));
            }
        }
        return s.pop();
    }
}

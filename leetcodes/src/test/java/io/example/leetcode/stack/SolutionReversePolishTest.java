package io.example.leetcode.stack;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SolutionReversePolishTest {

    @Test
    void case1() {
        var result = new SolutionReversePolish().evalRPN(new String[] {"2","1","+","3","*"});

        Assertions.assertThat(result).isEqualTo(9);
    }

    @Test
    void case2() {
        var result = new SolutionReversePolish().evalRPN(new String[] {"4","13","5","/","+"});

        Assertions.assertThat(result).isEqualTo(6);
    }

    @Test
    void case3() {
        var result = new SolutionReversePolish().evalRPN(new String[] {"10","6","9","3","+","-11","*","/","*","17","+","5","+"});

        Assertions.assertThat(result).isEqualTo(22);
    }
}
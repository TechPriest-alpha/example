package io.example.leetcode.bits;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SolutionBitsSum {
    public int getSum(int a, int b) {

        var abits = Integer.toBinaryString(a);
        var bbits = Integer.toBinaryString(b);
        System.out.println(abits.length() + " " + bbits.length());
        int result = 0;
        for (var i = 0; i < 32; i++) {

        }
        return 1;
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        var s1 = StreamSupport.stream(new CustomSpliterator(list1), false);
        var s2 = StreamSupport.stream(new CustomSpliterator(list2), false);
        var result = Stream.concat(s1, s2).sorted(Comparator.naturalOrder()).reduce((n1, n2) -> n1.next = n2).isPresent();
        return merger(list1, list2);

    }

    private ListNode merger(ListNode n1, ListNode n2) {
        if (n1 == null) {
            return n2;
        } else if (n2 == null) {
            return n1;
        } else if (n1.val <= n2.val) {
            n1.next = merger(n1.next, n2);
            return n1;
        } else {
            n2.next = merger(n1, n2.next);
            return n2;
        }
    }

    public static class CustomSpliterator implements Spliterator<ListNode> {
        private ListNode node;

        public CustomSpliterator(ListNode node) {
            this.node = node;
        }

        @Override
        public boolean tryAdvance(Consumer<? super ListNode> action) {
            if (node == null) {return false;} else {
                action.accept(node);
                node = node.next;
                return true;
            }
        }

        @Override
        public Spliterator<ListNode> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return Long.MAX_VALUE;
        }

        @Override
        public int characteristics() {
            return Spliterator.ORDERED;
        }
    }

    public static class ListNode implements Comparable<ListNode> {
        private int val;
        private ListNode next;

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public int compareTo(ListNode o) {
            return Comparator.<Integer>naturalOrder().compare(this.val, o.val);
        }

        public int val() {return val;}

        public ListNode next() {return next;}

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {return true;}
            if (obj == null || obj.getClass() != this.getClass()) {return false;}
            var that = (ListNode) obj;
            return this.val == that.val &&
                Objects.equals(this.next, that.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(val, next);
        }

        @Override
        public String toString() {
            return "ListNode[" +
                "val=" + val + ", " +
                "next=" + next + ']';
        }

    }
}

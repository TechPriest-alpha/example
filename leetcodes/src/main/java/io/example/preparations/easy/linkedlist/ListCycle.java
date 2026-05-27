package io.example.preparations.easy.linkedlist;

public class ListCycle {
    public boolean hasCycle(ListNode head) {
        var turtle = head;
        var hedgehog = head;
        while (hedgehog != null && hedgehog.next != null) {
            hedgehog = hedgehog.next.next;
            turtle = turtle.next;
            if (hedgehog == turtle) {return true;}
        }
        return false;
    }
}

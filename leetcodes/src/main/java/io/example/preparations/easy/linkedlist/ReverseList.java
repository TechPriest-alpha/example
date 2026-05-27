package io.example.preparations.easy.linkedlist;

public class ReverseList {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode a = head, b = null;
        while (a != null) {
            var temp = a.next;

            a.next = b;
            b = a;
            a = temp;
        }
        return b;
    }
}

package io.example.preparations.easy.linkedlist;

public class PalindromeList {
    public boolean isPalindrome(ListNode head) {
        var k = 0;
        var temp = head;
        while (temp != null) {
            temp = temp.next;
            k++;
        }
        var reversed = reverseList(head);
        temp = reversed;
        for (int i = 0; i < k / 2; i++) {temp = temp.next;}
        var subReversed = reverseList(temp);
        for (int i = 0; i < k / 2; i++) {
            if (reversed.val != subReversed.val) {return false;}
            reversed = reversed.next;
            subReversed = subReversed.next;
        }
        return true;
    }

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

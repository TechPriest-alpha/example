package io.example.preparations.easy.linkedlist;

public class RemoveNthNodeFromEnd {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        var pointer = head;
        var k = 0;
        while (pointer != null && k < n) {
            pointer = pointer.next;
            k++;
        }
        //System.out.println("k= " + k + ", node=" + pointer);
        if (pointer == null) {return head.next;}

        var pointer2 = head;
        while (pointer.next != null) {
            pointer2 = pointer2.next;
            pointer = pointer.next;
        }
        //System.out.println("k= " + k + ", node=" + pointer + ", node2=" + pointer2.val);
        if (pointer2.next != null) {pointer2.next = pointer2.next.next;}
        return head;
    }
}

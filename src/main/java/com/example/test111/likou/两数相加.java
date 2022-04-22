package com.example.test111.likou;


class ListNode {

  int val;
  ListNode next;

  ListNode() {
  }

  ListNode(int val) {
    this.val = val;
  }

  ListNode(int val, ListNode next) {
    this.val = val;
    this.next = next;
  }
}

public class 两数相加 {

  public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    return dfs(l1, l2, 0);
  }

  public static ListNode dfs(ListNode l1, ListNode l2, int i) {
    if (l1 == null && l2 == null && i == 0) {
      return null;
    }
    int sum = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val) + i;
    ListNode listNode = new ListNode(sum % 10);
    listNode.next = dfs(l1 != null ? l1.next : null, l2 != null ? l2.next : null, sum / 10);
    return listNode;
  }
}

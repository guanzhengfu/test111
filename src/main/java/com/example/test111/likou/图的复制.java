package com.example.test111.likou;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class 图的复制 {

  class Node {

    public int val;
    public List<Node> neighbors;

    public Node() {
      val = 0;
      neighbors = new ArrayList<Node>();
    }

    public Node(int _val) {
      val = _val;
      neighbors = new ArrayList<Node>();
    }

    public Node(int _val, ArrayList<Node> _neighbors) {
      val = _val;
      neighbors = _neighbors;
    }
  }

  private Map<Node, Node> map = new HashMap<>();

  public Node cloneGraph(Node node) {
    if (node == null) {
      return null;
    }
    dfs(node);

    map.forEach((k, v) -> {
      for (Node neighbor : k.neighbors) {
        v.neighbors.add(map.get(neighbor));
      }
    });

    return map.get(node);
  }

  private void dfs(Node node) {
    map.put(node, new Node(node.val));
    for (Node n : node.neighbors) {
      if (!map.containsKey(n)) {
        dfs(n);
      }
    }
  }

  public int minimumAverageDifference(int[] nums) {
    int n = nums.length;
    long[] sum = new long[n + 1];
    for (int i = 1; i <= n; i++) {
      sum[i] = sum[i - 1] + nums[i - 1];
    }

    int min = (int) 1e9;
    int[] avg = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      long left = sum[i], right = sum[n] - sum[i];
      avg[i] = (int) Math.abs(left / i - (i < n ? right / (n - i) : 0));
      min = Math.min(min, avg[i]);
    }

    for (int i = 1; i <= n; i++) {
      if (avg[i] == min) {
        return i - 1;
      }
    }

    return -1;
  }
}

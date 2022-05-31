package com.example.test111.likou;

import java.util.ArrayList;
import java.util.List;

public class 子集 {

  List<Integer> t = new ArrayList<Integer>();
  List<List<Integer>> ans = new ArrayList<List<Integer>>();

  public List<List<Integer>> subsets(int[] nums) {
    dfs(0, nums);
    return ans;
  }

  private void dfs(int cur, int[] nums) {
    ans.add(new ArrayList<>(t));
    for (int i = cur; i < nums.length; i++) {
      t.add(nums[i]);
      dfs(i + 1, nums);
      t.remove(t.size() - 1);
    }
  }
}

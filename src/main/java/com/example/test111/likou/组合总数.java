package com.example.test111.likou;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class 组合总数 {

  private List<List<Integer>> res = new ArrayList<>();

  public List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<Integer> path = new ArrayList<>();
    Arrays.sort(candidates);
    backtrack(path, candidates, target, 0, 0);
    return res;
  }

  private void backtrack(List<Integer> path, int[] candidates, int target, int sum, int begin) {
    if (sum == target) {
      res.add(new ArrayList<>(path));
      return;
    }
    for (int i = begin; i < candidates.length; i++) {
      int temp = candidates[i] + sum;
      if (temp <= target) {
        path.add(candidates[i]);
        backtrack(path, candidates, target, temp, i);
        path.remove(path.size() - 1);
      } else {
        break;
      }
    }
  }
}

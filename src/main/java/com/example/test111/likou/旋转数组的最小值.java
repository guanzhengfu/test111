package com.example.test111.likou;

public class 旋转数组的最小值 {

  public int findMin(int[] nums) {
    int low = 0;
    int height = nums.length - 1;
    while (low < height) {
      int mid = (low + height) / 2;
      if (nums[mid] < nums[height]) {
        height = mid;
      } else {
        low = mid + 1;
      }
    }
    return nums[low];
  }
}

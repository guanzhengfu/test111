package com.example.test111.likou;

import java.util.Scanner;

public class 快排 {

  public static void quickSort(int left, int right, int arr[]) {
    if (left > right) {
      return;
    }
    int temp = arr[left];
    int i = left;
    int j = right;
    while (i != j) {
      while (arr[j] >= temp && j > i) {
        j--;
      }
      while (arr[i] <= temp && j > i) {
        i++;
      }
      if (j > i) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
      }
    }
    arr[left] = arr[i];
    arr[i] = temp;
    quickSort(left, i - 1, arr);
    quickSort(i + 1, right, arr);
  }

  public static void main(String[] args) {
    int[] arr = new int[6];
    Scanner sc = new Scanner(System.in);
    for (int i = 0; i < arr.length; i++) {
      arr[i] = sc.nextInt();
    }
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i] + " ");
    }
    quickSort(0, arr.length - 1, arr);
    System.out.println("\n排序后数组如下：");
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i] + " ");
    }
  }

}

package com.example.test111.likou;

import com.example.test111.bean.ErrorResult;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Test {


  public static void main(String[] args) {
    ArrayList<ErrorResult> errorResults = new ArrayList<>();
    errorResults.add(new ErrorResult("test"));
    errorResults.add(new ErrorResult("test"));
    errorResults.add(new ErrorResult("test"));
    errorResults.add(new ErrorResult("test"));
    System.out.println(errorResults);
    ArrayList<ErrorResult> errorResults1 = new ArrayList<>(
        errorResults.stream().collect(Collectors.toCollection(
            () -> new TreeSet<>(Comparator.comparing(ErrorResult::getMessage)))));
    System.out.println(errorResults1);
  }
}

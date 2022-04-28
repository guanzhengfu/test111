package com.example.test111.likou;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class 基本计算器 {

  static class Operation {

    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    public static int getValue(String operation) {
      int result = 0;
      switch (operation) {
        case "+":
          result = ADD;
          break;
        case "-":
          result = SUB;
          break;
        case "*":
          result = MUL;
          break;
        case "/":
          result = DIV;
          break;
        default:
          System.out.println("不存在");
          break;
      }
      return result;
    }
  }

  public static List<String> parseSuffixExpressionList(List<String> ls) {
    //定义两个栈
    Stack<String> s1 = new Stack<String>();  //符号栈
    List<String> s2 = new ArrayList<String>();  //结果

    for (String item : ls) {
      if (item.matches("\\d")) {
        s2.add(item);
      } else if (item.equals("(")) {
        s2.add(item);
      } else if (item.equals(")")) {
        while (!s1.peek().equals("(")) {
          s2.add(s1.pop());
        }
        s1.pop();
      } else {
        while (s1.size() != 0 && Operation.getValue(item)<= Operation.getValue(s1.peek())){
          s2.add(s1.pop());
        }
        s1.push(item);
      }
    }
    while (s1.size() != 0) {
      s2.add(s1.pop());
    }
    return s2;
  }

}

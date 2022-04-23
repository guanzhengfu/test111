package com.example.test111.util;

import static com.example.test111.util.EncodeUtil.encrypt;

public class Test {

  public static void main(String[] args) {
    String encrypt = encrypt("test",
        "");
    System.out.println(encrypt);
  }
}

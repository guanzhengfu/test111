package com.example.test111.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

  @GetMapping("/test")
  public String test(){
    return "new test";
  }
}

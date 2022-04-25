package com.example.test111.controller;

import com.example.test111.application.ProductScheduledPublishApplication;
import com.example.test111.request.ScheduledPublishRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.nio.charset.Charset;
import java.util.Random;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
public class test {

  @Autowired
  ProductScheduledPublishApplication productScheduledPublishApplication;

  @PostMapping("/{time}/scheduled-publishing")
  @ApiOperation("scheduled-publishing")
  public void scheduledPublish(@PathVariable("time") Long time)
      throws SchedulerException, JsonProcessingException {
    ScheduledPublishRequest request = new ScheduledPublishRequest();
    request.setScheduledPublishTime(time);
    Random random = new Random();
    Long id = random.nextLong() * 1000;
    productScheduledPublishApplication.scheduledPublish(id, request.getScheduledPublishTime());
  }

  @GetMapping("/test/{token}")
  @ApiOperation("get username by token")
  public String test(@PathVariable(value = "token") String token) {
    Claims body = null;
    //获取username,然后
    try {
      body = Jwts.parser()
          .setSigningKey("signingKey".getBytes(Charset.defaultCharset()))
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
    }
   return "test";
  }

  @GetMapping("/test1/2")
  @ApiOperation("Get test result.")
  public String test1() {
//    throw new RuntimeException();
    return "test1";
  }
}

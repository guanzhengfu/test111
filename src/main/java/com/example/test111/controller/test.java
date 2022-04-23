package com.example.test111.controller;

import com.example.test111.application.ProductScheduledPublishApplication;
import com.example.test111.request.ScheduledPublishRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Random;
import liquibase.pro.packaged.A;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

  @Autowired
  ProductScheduledPublishApplication productScheduledPublishApplication;

  @PostMapping("/{time}/scheduled-publishing")
  public void scheduledPublish(@PathVariable("time") Long time)
      throws SchedulerException, JsonProcessingException {
    ScheduledPublishRequest request = new ScheduledPublishRequest();
    request.setScheduledPublishTime(time);
    Random random = new Random();
    Long id = random.nextLong() * 1000;
    productScheduledPublishApplication.scheduledPublish(id, request.getScheduledPublishTime());
  }

  @GetMapping("/test/{token}")
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
}

package com.example.test111.controller;

import com.example.test111.application.ProductScheduledPublishApplication;
import com.example.test111.request.ScheduledPublishRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Random;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
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

  @GetMapping("/test")
  public String test(){
    return "test";
  }
}

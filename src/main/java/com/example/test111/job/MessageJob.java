package com.example.test111.job;

import com.example.test111.messageHandle.MessageHandler;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;

@Slf4j
@Setter
public class MessageJob implements Job {

  private ApplicationContext applicationContext;

  private String payload;

  private String destination;

  @SneakyThrows
  @Override
  public void execute(JobExecutionContext context) {
    log.info("quartz job payload: " + payload + ", destination: " + destination);
    MessageHandler messageHandler = (MessageHandler) applicationContext.getBean(destination);
    messageHandler.handlerMessage(payload);
  }

}

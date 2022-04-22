package com.example.test111.messageHandle;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutoPublishProductHandler implements MessageHandler {


  @Override
  public void handlerMessage(String payload) throws JsonProcessingException {
//    AutoPublishProductPayload autoPublishProductPayload = new ObjectMapper().readValue(payload, AutoPublishProductPayload.class);
    System.out.println(System.currentTimeMillis() + " " +payload);
    System.out.println("test 99");
  }
}

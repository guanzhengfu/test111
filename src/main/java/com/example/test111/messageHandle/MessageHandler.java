package com.example.test111.messageHandle;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface MessageHandler {

  void handlerMessage(String payload) throws JsonProcessingException;

}

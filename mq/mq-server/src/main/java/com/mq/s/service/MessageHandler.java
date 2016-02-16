package com.mq.s.service;

import java.util.HashMap;

import com.mq.c.model.MyMessage;

public interface MessageHandler {
    
    void handleMessage(String message);  
  
    void handleMessage(HashMap<String, Object> message);  
  
    void handleMessage(byte[] message);  
  
    void handleMessage(MyMessage message);  
}

package com.mq.s.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.mq.c.model.MyMessage;
import com.mq.s.service.MessageHandler;

public class JmsReceiver implements MessageHandler{
      
    public void handleMessage(String message) {  
        System.out.println(message);  
    }  
  
    public void handleMessage(HashMap<String, Object> message) {  
        Set<String> keySet = message.keySet();  
        Iterator<String> keys = keySet.iterator();  
        while (keys.hasNext()) {  
            String key = keys.next();  
            System.out.println(message.get(key));  
        }  
  
    }  
  
    public void handleMessage(byte[] message) {  
    	System.out.println(message);
    }  
  
    public void handleMessage(MyMessage message) {  
    	System.out.println(message);
    }  
  
}

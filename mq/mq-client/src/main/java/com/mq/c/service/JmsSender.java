package com.mq.c.service;

import java.util.Collection;  
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;  
import javax.jms.JMSException;  
import javax.jms.MapMessage;
import javax.jms.Message;  
import javax.jms.ObjectMessage;
import javax.jms.Session;  

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.core.task.TaskExecutor;  
import org.springframework.jms.core.JmsTemplate;  
import org.springframework.jms.core.MessageCreator;  
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;  

import com.mq.c.model.MyMessage;
 
@Service
public class JmsSender {
	@Autowired  
    private JmsTemplate jmsTemplate;  
          
    @Autowired  
    private TaskExecutor taskExecutor;  
    
    @Resource(name="asyncTopic")
    private Destination destination;  
  
    private boolean isSendAsync = false;
    
    /**
     * 批量推送消息
     * @param messages
     */
    public void sendTextBatch(Collection<?> messages) {  
        Assert.notNull(messages, "param 'messages' can't be null !");  
        Assert.notEmpty(messages, "param 'message' can't be empty !");  
        for (Object message : messages) {  
            if (null != message && message instanceof String) {  
                sendTextSingle(String.valueOf(message));  
            }  
        }  
    } 
    
    /**
     * 单个推送消息
     * @param messages
     */
    public void sendTextSingle(String message) {  
        sendText(message);  
    }  
    
    /**
     * 处理异步或同步消息推送
     * @param messages
     */
    private void sendText(final String message) {  
        if (isSendAsync) {  
            taskExecutor.execute(new Runnable() {  
                @Override  
                public void run() {  
                    send(message);  
                }  
            });  
        } else {  
            send(message);  
        }  
    }
    
    /**
     * 推送消息
     * @param messages
     */
    private void send(final String message) {
        this.jmsTemplate.send(destination, new MessageCreator() {
            @Override  
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }  
  
    
    
    
    /**
     * 批量推送map消息
     * @param messages
     */
    public void sendMapBatch(Collection<Map<String, Object>> messages) {  
        for (Map<String, Object> message : messages) {  
            if (null != message && message instanceof Map) {  
                sendMapSingle(message);  
            }  
        }  
    } 
    
    /**
     * 单个推送map消息
     * @param messages
     */
    public void sendMapSingle(Map<String, Object> message) {  
        sendMap(message);  
    }
    
    /**
     * 同步或者异步推送map消息
     * @param messages
     */
    private void sendMap(final Map<String, Object> message) {  
        if (isSendAsync) {  
            taskExecutor.execute(new Runnable() {  
                @Override  
                public void run() {  
                    send(message);  
                }  
            });  
        } else {  
            send(message);  
        }  
    } 
    
    /**
     * 推送消息
     * @param messages
     */
    private void send(final Map<String, Object> message) {
        this.jmsTemplate.send(destination, new MessageCreator() {
            @Override  
            public Message createMessage(Session session) throws JMSException {
                		MapMessage mm = session.createMapMessage();
                		for(String key : message.keySet()){
                			mm.setString(key, (String) message.get(key));
                		}
                		return mm;
            }
        });
    }  
    
    
    /**
     * 批量推送Object消息
     * @param messages
     */
    public void sendObjectBatch(Collection<MyMessage> messages) {  
        for (MyMessage message : messages) {  
            if (null != message && message instanceof MyMessage) {  
                sendObjectSingle(message);  
            }  
        }  
    } 
    
    /**
     * 单个推送Object消息
     * @param messages
     */
    public void sendObjectSingle(MyMessage message) {  
    	sendObject(message);  
    }
    
    /**
     * 同步或者异步推送Object消息
     * @param messages
     */
    private void sendObject(final MyMessage message) {  
        if (isSendAsync) {  
            taskExecutor.execute(new Runnable() {  
                @Override  
                public void run() {  
                    send(message);  
                }  
            });  
        } else {  
            send(message);  
        }  
    } 
    
    /**
     * 推送消息
     * @param messages
     */
    private void send(final MyMessage message) {
        this.jmsTemplate.send(destination, new MessageCreator() {
            @Override  
            public Message createMessage(Session session) throws JMSException {
            	ObjectMessage mm = session.createObjectMessage();
                mm.setObject(message);		
                return mm;
            }
        });
    } 
    
    public boolean isSendAsync() {  
        return isSendAsync;  
    }  
      
    public void setSendAsync(boolean isSendAsync) {  
        this.isSendAsync = isSendAsync;  
    }  
}

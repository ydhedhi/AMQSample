package com.example.amq.message.listener;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

/**
 * Listener class which will listening on destination queue for the upcoming message
 * @author Yogesh yogeshdhedhi@gmail.com
 *
 */
public class MyMessageListener implements MessageListener {

  private static final Logger logger = Logger.getLogger(MyMessageListener.class);

  public void onMessage(Message message) {
      try{
    	  if(message instanceof MapMessage) {
    		  MapMessage mapMessage = (MapMessage)message;
    		  Map map = (Map)mapMessage.getObject("Map");
    		  logger.debug("PartId: "+map.get("partId"));
    		  logger.debug("PartName: "+map.get("partName"));
    	  } else {
    		   TextMessage msg = (TextMessage) message;
    	       logger.info("Message: " + msg.getText());
    	  }
      } catch (JMSException e) {
    	  logger.debug("Exception: while reading message From Queue "+e);
      }
  }
}
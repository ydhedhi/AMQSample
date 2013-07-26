package com.example.amq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.JMSException;

import org.apache.log4j.Logger;

import com.example.amq.message.MessageProducer;

/**
 * Sender class which will concrete the message and send to queue.
 * @author Yogesh yogeshdhedhi@gmail.com
 *
 */
public class MessageSender {
	
	/**
     * Logger instance
     */
    private static final Logger logger = Logger
            .getLogger(MessageSender.class);
    
    private MessageProducer messageProducer;
    
	public void  init(){
		logger.debug("init() called........");
		try {
			sendTextMesage();
		} catch (JMSException e) {
			logger.debug("Exception: "+e);
		}
	}

	private void sendTextMesage() throws JMSException {
		logger.debug("sending Text Message to Queue...");
		messageProducer.sendTextMessage("part.change", "queue", "Hi This is text message");
		logger.debug("Text message Sent......");
		
		logger.debug("sending Map Message to Queue...");
		Map<String, String > mapMessage = new ConcurrentHashMap();
		mapMessage.put("partId", "1");
		mapMessage.put("partName", "Valve");
		mapMessage.put("cycleTime", "30");
		mapMessage.put("delayTime", "15");
		
		messageProducer.sendMapMessage("part.change", "Map", mapMessage);
		logger.debug("Map message Sent......");
	}

	/**
	 * @param messageProducer the messageProducer to set
	 */
	public void setMessageProducer(MessageProducer messageProducer) {
		this.messageProducer = messageProducer;
	}

	/**
	 * @return the messageProducer
	 */
	public MessageProducer getMessageProducer() {
		return messageProducer;
	}
}

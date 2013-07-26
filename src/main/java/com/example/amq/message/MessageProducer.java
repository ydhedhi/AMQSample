package com.example.amq.message;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Class to send message to active mq queue destination.
 *
 * The sent message will have property "message_type" which can be used by the sender
 * and the receiver to decide the message activity.
 *
 * @author Yogesh yogeshdhedhi@gmail.com
 */

public class MessageProducer {

    public static final String MESSAGE_TYPE = "message_type";
    
    /**
     * Logger instance
     */
    private static final Logger logger = Logger
            .getLogger(MessageProducer.class);

    public MessageProducer() {
    }

    @Autowired
    private JmsTemplate producerTemplate;

    /**
	 * @return the producerTemplate
	 */
	public JmsTemplate getProducerTemplate() {
		return producerTemplate;
	}

	/**
	 * @param producerTemplate the producerTemplate to set
	 */
	public void setProducerTemplate(JmsTemplate producerTemplate) {
		this.producerTemplate = producerTemplate;
	}

	/**
     * Send the map like message to the given destination.
     * 
     * @param destination
     * @param messageType String to identify the type of the message so that the
     *                    receiver can make sense out of it. 
     * @param message
     * @throws JMSException
     */
    public void sendMapMessage(String destination,
                               final String messageType,
                               final Map<String, String> message) throws JMSException {
        logger.debug("Sending map message to destination: " + destination);
        producerTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                //mapMessage.setString(MESSAGE_TYPE, messageType);
                mapMessage.setObject(messageType, message);
                Set<String> keySet = message.keySet();
                Iterator<String> iterator = keySet.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    mapMessage.setString(key, message.get(key));
                }
                return mapMessage;
            }
        });
        logger.debug("Map message sent successfully to destination: " + destination);
    }

    /**
     * Send the text message to the given destination.
     *
     * @param destination
     * @param text
     * @throws JMSException
     */
    public void sendTextMessage(String destination,
                                final String messageType,
                                final String text) throws JMSException {
        logger.debug("Sending text message to destination: " + destination);
        producerTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage txtMessage = session.createTextMessage(text);
                txtMessage.setStringProperty(MESSAGE_TYPE, messageType);
                return txtMessage;
            }
        });
        logger.debug("Text message sent successfully to destination: " + destination);
    }
}

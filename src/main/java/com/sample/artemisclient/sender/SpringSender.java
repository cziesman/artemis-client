package com.sample.artemisclient.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class SpringSender {

    private static final Logger LOG = LoggerFactory.getLogger(SpringSender.class);

    @Autowired
    @Qualifier("ArtemisJmsTemplate")
    public JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @Scheduled(fixedDelay = 5000L)
    public void send() {

        try {
            jmsTemplate.convertAndSend(queue, "Hello");
            LOG.info("Sent Spring message");
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }
}

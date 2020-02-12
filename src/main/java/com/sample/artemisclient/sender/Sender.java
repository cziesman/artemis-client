package com.sample.artemisclient.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class Sender {

    private static final Logger LOG = LoggerFactory.getLogger(Sender.class);

    @Value("${artemis.user}")
    private String user;

    @Value("${artemis.password}")
    private String password;

    @Autowired
    @Qualifier("ArtemisConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Autowired
    private Destination queue;

    @Scheduled(fixedDelay = 60000L)
    public void send() {

        try {
            Connection connection = connectionFactory.createConnection(user, password);

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(queue);
            TextMessage message = session.createTextMessage("Hello");

            connection.start();
            messageProducer.send(message);
            LOG.info("Sent message");

            messageProducer.close();
            session.close();
            connection.close();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }
}
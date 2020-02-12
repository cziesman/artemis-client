package com.sample.artemisclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

@Configuration
class ArtemisConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ArtemisConfig.class);

    @Value("${java.naming.factory.initial}")
    private String contextFactory;

    @Value("${java.naming.provider.url}")
    private String remotingUrl;

    @Value("${artemis.connection.factory}")
    private String artemisConnectionFactory;

    @Value("${artemis.queue}")
    private String artemisQueue;

    @Bean(name = "ArtemisConnectionFactory")
    public ConnectionFactory remoteConnectionFactory() throws Exception {

        return (ConnectionFactory) initialContext().lookup(artemisConnectionFactory);
    }

    @Bean
    public Queue queue() throws Exception {

        return (Queue) initialContext().lookup(artemisQueue);
    }

    private InitialContext initialContext() throws Exception {

        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
        props.put(Context.PROVIDER_URL, remotingUrl);
        InitialContext ctx = new InitialContext(props);

        return ctx;
    }
}

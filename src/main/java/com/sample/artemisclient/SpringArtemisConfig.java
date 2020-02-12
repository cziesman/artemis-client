package com.sample.artemisclient;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
class SpringArtemisConfig {

    @Value("${artemis.user}")
    private String user;

    @Value("${artemis.password}")
    private String password;

    @Bean(name = "ArtemisJmsTemplate")
    public JmsTemplate artemisJmsTemplate(@Qualifier("ArtemisCredentialedConnectionFactory") ConnectionFactory connectionFactory) {

        return new JmsTemplate(connectionFactory);
    }

    @Bean(name = "ArtemisCredentialedConnectionFactory")
    public ConnectionFactory connectionFactory(@Qualifier("ArtemisConnectionFactory") ConnectionFactory connectionFactory) {

        UserCredentialsConnectionFactoryAdapter adapter = new UserCredentialsConnectionFactoryAdapter();
        adapter.setUsername(user);
        adapter.setPassword(password);
        adapter.setTargetConnectionFactory(connectionFactory);

        adapter.afterPropertiesSet();

        return adapter;
    }
}

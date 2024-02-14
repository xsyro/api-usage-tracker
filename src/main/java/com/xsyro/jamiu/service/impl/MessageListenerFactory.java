package com.xsyro.jamiu.service.impl;

import com.xsyro.jamiu.service.IngressService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MessageListenerFactory {

    private final ConnectionFactory connectionFactory;

    @Bean
    SimpleMessageListenerContainer listenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setQueueNames(IngressServiceImpl.QUEUE_NAME);
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(listenerAdapter);
//        container.setTaskExecutor();
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(IngressService receiver) {
        return new MessageListenerAdapter(receiver, "createBillingRecord");
    }

}

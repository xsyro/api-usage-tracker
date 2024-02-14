package com.xsyro.jamiu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xsyro.jamiu.service.impl.IngressServiceImpl;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.RabbitFlux;
import reactor.rabbitmq.Sender;
import reactor.rabbitmq.SenderOptions;

import java.util.Map;

@Configuration
public class RabbitMqConfig {

    @Bean
    Mono<Connection> connectionMono() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.useNio();
        return Mono.fromCallable(() -> connectionFactory.newConnection("reactor-rabbit")).cache();
    }


    @Bean
    public MessageConverter jsonToMapMessageConverter(ObjectMapper objectMapper) {
        Jackson2JsonMessageConverter messageConverter = new ImplicitJsonMessageConverter(objectMapper);
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("*");
        classMapper.setDefaultType(Map.class);
        messageConverter.setClassMapper(classMapper);
        return messageConverter;
    }

    public static class ImplicitJsonMessageConverter extends Jackson2JsonMessageConverter {
        public ImplicitJsonMessageConverter(ObjectMapper jsonObjectMapper) {
            super(jsonObjectMapper, "*");
        }
        @Override
        public Object fromMessage(Message message) throws MessageConversionException {
            message.getMessageProperties().setContentType("application/json");
            return super.fromMessage(message);
        }
    }
}

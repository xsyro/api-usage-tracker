package com.xsyro.jamiu;

import com.rabbitmq.client.Connection;
import com.xsyro.jamiu.config.ExchangeBinding;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Objects;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
@EnableRabbit
public class StartServer {

    private final Mono<Connection> connectionMono;

    private final ExchangeBinding exchangeBinding;

    private final AmqpAdmin amqpAdmin;


    public static void main(String[] args) {
        SpringApplication.run(StartServer.class, args);
    }

    @PostConstruct
    public void init() {
        exchangeBinding.getQueues().forEach((s, queueInfo) -> {
            Exchange exchange = ExchangeBuilder.directExchange(
                            queueInfo.getExchange())
                    .durable(true)
                    .build();
            amqpAdmin.declareExchange(exchange);
            Queue q = QueueBuilder.durable(
                            queueInfo.getRoutingKey())
                    .build();
            amqpAdmin.declareQueue(q);
            Binding b = BindingBuilder.bind(q)
                    .to(exchange)
                    .with(queueInfo.getRoutingKey())
                    .noargs();
            amqpAdmin.declareBinding(b);
        });
    }


    @PreDestroy
    public void release() throws IOException {
        log.debug("Closing AMQP Connection");
        Objects.requireNonNull(connectionMono.block()).close();
    }
}

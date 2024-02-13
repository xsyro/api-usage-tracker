package com.xsyro.jamiu;

import com.rabbitmq.client.Connection;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Objects;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class StartServer {

    private final Mono<Connection> connectionMono;

    public static void main(String[] args) {
        SpringApplication.run(StartServer.class, args);
    }


    @PreDestroy()
    public void closeConnection() throws IOException {
        log.debug("Closing AMQP Connection");
        Objects.requireNonNull(connectionMono.block()).close();
    }
}

package com.xsyro.jamiu.service.impl;

import com.xsyro.jamiu.config.ExchangeBinding;
import com.xsyro.jamiu.model.Egress;
import com.xsyro.jamiu.model.IngressEnvelope;
import com.xsyro.jamiu.service.IngressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.xsyro.jamiu.model.Egress.ERROR_CODE_NOT_FOUND;
import static com.xsyro.jamiu.model.Egress.OK;

@Service
@AllArgsConstructor
@Slf4j
public class IngressServiceImpl implements IngressService {

    public static final String QUEUE_NAME = "billing-service";

    private final AmqpTemplate amqpTemplate;

    private final ExchangeBinding exchangeBinding;

    @Override
    public Mono<Egress> ackBilling(IngressEnvelope ingressEnvelope) {
        ExchangeBinding.QueueInfo queueInfo = exchangeBinding.getQueues().get(QUEUE_NAME);
        if (Objects.isNull(queueInfo))
            return Mono.just(Egress.builder()
                    .isSuccessful(false)
                    .code(ERROR_CODE_NOT_FOUND)
                    .msg("Unable to find billing queue. Please contact technical team.")
                    .build());


        return Mono.fromCallable(() -> {
            amqpTemplate.convertAndSend(queueInfo.getExchange(), queueInfo.getRoutingKey(), ingressEnvelope);
            return Egress.builder().isSuccessful(true).code(OK).msg("Billing data received").build();
        });
    }

    public void persist(IngressEnvelope message) {
        System.out.println("Received <" + message.getEndpoint() + ">");
    }
}

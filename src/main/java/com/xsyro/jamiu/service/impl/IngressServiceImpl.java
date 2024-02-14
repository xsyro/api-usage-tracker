package com.xsyro.jamiu.service.impl;

import com.xsyro.jamiu.config.ExchangeBinding;
import com.xsyro.jamiu.model.Egress;
import com.xsyro.jamiu.model.Ingress;
import com.xsyro.jamiu.service.IngressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.xsyro.jamiu.model.Egress.ERROR_CODE_NOT_FOUND;
import static com.xsyro.jamiu.model.Egress.OK;

@Service
@AllArgsConstructor
@Slf4j
public class IngressServiceImpl implements IngressService {

    private static final String QUEUE_NAME = "billing-service";

    private final AmqpTemplate amqpTemplate;

    private final ExchangeBinding exchangeBinding;

    @Override
    public Mono<Egress> ackBilling(Ingress ingress) {
        ExchangeBinding.QueueInfo queueInfo = exchangeBinding.getQueues().get(QUEUE_NAME);
        if (Objects.isNull(queueInfo))
            return Mono.just(Egress.builder()
                    .isSuccessful(false)
                    .code(ERROR_CODE_NOT_FOUND)
                    .msg("Unable to find billing queue. Please contact technical team.")
                    .build());


        return Mono.fromCallable(() -> {
            amqpTemplate.convertAndSend(queueInfo.getExchange(), queueInfo.getRoutingKey(), ingress);
            return Egress.builder().isSuccessful(true).code(OK).msg("Billing data received").build();
        });
    }


}

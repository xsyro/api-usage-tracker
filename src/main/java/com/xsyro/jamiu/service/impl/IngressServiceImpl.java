package com.xsyro.jamiu.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsyro.jamiu.config.ExchangeBinding;
import com.xsyro.jamiu.model.Egress;
import com.xsyro.jamiu.model.IngressEnvelope;
import com.xsyro.jamiu.model.UsageActivityLog;
import com.xsyro.jamiu.repository.UsageActivityLogRepository;
import com.xsyro.jamiu.service.IngressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import static com.xsyro.jamiu.model.Egress.ERROR_CODE_NOT_FOUND;
import static com.xsyro.jamiu.model.Egress.OK;

@Service
@AllArgsConstructor
@Slf4j
public class IngressServiceImpl implements IngressService {

    public static final String QUEUE_NAME = "billing-service";

    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    private final ExchangeBinding exchangeBinding;
    private final MessageListenerFactory messageListenerFactory;
    private final UsageActivityLogRepository usageActivityLogRepository;

    @Override
    public Mono<Egress> ackBilling(IngressEnvelope ingressEnvelope) {
        var sessionId = System.currentTimeMillis();
        ingressEnvelope.setSession(sessionId);
        ExchangeBinding.QueueInfo queueInfo = exchangeBinding.getQueues().get(QUEUE_NAME);
        if (Objects.isNull(queueInfo))
            return Mono.just(Egress.builder()
                    .isSuccessful(false)
                    .code(ERROR_CODE_NOT_FOUND)
                    .session(sessionId)
                    .msg("Unable to find billing queue. Please contact technical team.")
                    .build());


        return Mono.fromCallable(() -> {
            amqpTemplate.convertAndSend(queueInfo.getExchange(), queueInfo.getRoutingKey(), ingressEnvelope);
            return Egress.builder().isSuccessful(true).code(OK).msg("Billing data received").build();
        });
    }


    public void createBillingRecord(byte[] message) throws IOException {
        log.info("received msg from [AMQ] {}", new String(message));
        IngressEnvelope ingressEnvelope = objectMapper.readValue(message, IngressEnvelope.class);

        var record = UsageActivityLog.builder()
                .createdAt(new Date(System.currentTimeMillis()))
                .customerId(ingressEnvelope.getCustomerId())
                .serviceTag(ingressEnvelope.getServiceTag())
                .extras(objectMapper.writeValueAsString(ingressEnvelope.getExtras()))
                .requestTime(new Date(ingressEnvelope.getTimestamp()))
                .sessionRef(ingressEnvelope.getSession())
                .url(ingressEnvelope.getEndpoint())
                .build();
        usageActivityLogRepository.save(record);
        log.info("billing for session [{}] record saved!", ingressEnvelope.getSession());
    }
}

package com.xsyro.jamiu.service.impl;

import com.rabbitmq.client.Connection;
import com.xsyro.jamiu.model.Egress;
import com.xsyro.jamiu.model.Ingress;
import com.xsyro.jamiu.service.IngressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class IngressServiceImpl implements IngressService {

    private static final String QUEUE_NAME = "api-billing-service";

    @Override
    public Mono<Egress> ingestBillingData(Ingress ingress) {

        return Mono.empty();
    }


}

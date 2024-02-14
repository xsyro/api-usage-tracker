package com.xsyro.jamiu.service;


import com.xsyro.jamiu.model.Egress;
import com.xsyro.jamiu.model.IngressEnvelope;
import reactor.core.publisher.Mono;

public interface IngressService {
    Mono<Egress> ackBilling(IngressEnvelope ingressEnvelope);

}

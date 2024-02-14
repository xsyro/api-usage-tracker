package com.xsyro.jamiu.service;


import com.xsyro.jamiu.model.Egress;
import com.xsyro.jamiu.model.Ingress;
import reactor.core.publisher.Mono;

public interface IngressService {
    Mono<Egress> ackBilling(Ingress ingress);

}

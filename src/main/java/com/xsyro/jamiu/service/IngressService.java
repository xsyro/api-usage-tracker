package com.xsyro.jamiu.services;


import com.xsyro.jamiu.payload.Egress;
import com.xsyro.jamiu.payload.Ingress;
import reactor.core.publisher.Mono;

public interface IngressService {
    Mono<Egress> ingestData(Ingress ingress);

}

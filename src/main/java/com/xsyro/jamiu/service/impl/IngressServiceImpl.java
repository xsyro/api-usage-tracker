package com.xsyro.jamiu.services.impl;

import com.xsyro.jamiu.exception.InvalidIngressException;
import com.xsyro.jamiu.payload.Egress;
import com.xsyro.jamiu.payload.Ingress;
import com.xsyro.jamiu.services.IngressService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class IngressServiceImpl implements IngressService {


    @Override
    public Mono<Egress> ingestData(Ingress ingress) {
        return this.validate(ingress)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new InvalidIngressException("Invalid Request"));
                    }
                    return Mono.empty();
                });

    }


    private Mono<Boolean> validate(Ingress ingress) {

        return Mono.just(true);
    }

}

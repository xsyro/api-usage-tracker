package com.xsyro.jamiu.service.impl;

import com.xsyro.jamiu.exception.InvalidIngressException;
import com.xsyro.jamiu.model.Egress;
import com.xsyro.jamiu.model.Ingress;
import com.xsyro.jamiu.service.IngressService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class IngressServiceImpl implements IngressService {


    @Override
    public Mono<Egress> ingestData(Ingress ingress) {
        return Mono.empty();

    }



}

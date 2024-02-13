package com.xsyro.jamiu.controller;

import com.xsyro.jamiu.model.Egress;
import com.xsyro.jamiu.model.Ingress;
import com.xsyro.jamiu.service.IngressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/api/api-billing")
public class HttpController {
    private final IngressService ingressService;


    public HttpController(IngressService ingressService) {
        this.ingressService = ingressService;
    }

    @PostMapping("/ingest")
    private Mono<ResponseEntity<Egress>> ingest(@Valid @RequestBody Ingress ingress) {
//        ingressService.ingestData(ingress)
//                .onErrorReturn(throwable ->)
        return Mono.just(ResponseEntity.ok(Egress.builder().isSuccessful(true).msg("OK").build()));
    }

}

package com.xsyro.jamiu.ingress;

import com.xsyro.jamiu.model.Egress;
import com.xsyro.jamiu.model.IngressEnvelope;
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
public class BillingListener {
    private final IngressService ingressService;


    public BillingListener(IngressService ingressService) {
        this.ingressService = ingressService;
    }

    @PostMapping("/ingest")
    private Mono<ResponseEntity<Egress>> ingest(@Valid @RequestBody IngressEnvelope ingressEnvelope) {
        return ingressService.ackBilling(ingressEnvelope)
                .map(egress -> egress.getIsSuccessful() ? ResponseEntity.ok(egress) : ResponseEntity.badRequest().body(egress));
    }

}

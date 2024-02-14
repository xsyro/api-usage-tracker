package com.xsyro.jamiu.controller;

import com.xsyro.jamiu.repository.UsageActivityLogRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class BillingReportController {

    private final UsageActivityLogRepository usageActivityLogRepository;

    //TODO
    @GetMapping("/report/{customerId}")
    public Mono<ResponseEntity<Reporter>> customerReport(@PathVariable("customerId") Long customerId) {
        return Mono.just(usageActivityLogRepository.countByCustomerId(customerId))
                .map(requestCount -> {

//                    apiPricingDbRecord().keySet().stream()
//                            .sorted((o1, o2) -> Math.toIntExact(o1 - o2))
//                            .filter(aLong -> )

                    return Reporter.builder()
                            .customerId(customerId)
                            .totalApiCalls(requestCount)
//                            .amount()
                            .build();
                }).map(t -> ResponseEntity.ok(t));
    }


    @Data
    @Builder
    public static class Reporter {
        private Long customerId;
        private Long totalApiCalls;
        private BigDecimal amount;
    }

    private Map<Long, BigDecimal> apiPricingDbRecord() {
        Map<Long, BigDecimal> apiBound = new HashMap<>(3);
        apiBound.put(1000000L, new BigDecimal("5.0"));
        apiBound.put(10000000L, new BigDecimal("4.2"));
        apiBound.put(100000000L, new BigDecimal("3.5"));
        return apiBound;
    }

}

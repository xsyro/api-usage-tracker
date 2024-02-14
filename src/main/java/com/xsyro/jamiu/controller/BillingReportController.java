package com.xsyro.jamiu.controller;

import com.xsyro.jamiu.repository.UsageActivityLogRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
public class BillingReportController {

    private final UsageActivityLogRepository usageActivityLogRepository;

    //TODO
    @GetMapping("/report/{id}")
    public Mono<ResponseEntity<Reporter>> customerReport(@PathVariable("id") Long customerId) {
        return Mono.just(usageActivityLogRepository.countByCustomerId(customerId))
                .map(apiRequestCount -> {
                    Reporter.ReporterBuilder reporterBuilder = Reporter.builder()
                            .customerId(customerId)
                            .totalApiCalls(apiRequestCount);
                    Optional<Long> customerCurrentBillingPlanOption = apiPricingDbRecord().keySet().stream()
                            .sorted((o1, o2) -> Math.toIntExact(o1 - o2))
                            .filter(n -> n > apiRequestCount)
                            .findFirst();
                    if (customerCurrentBillingPlanOption.isEmpty()) {
                        reporterBuilder
                                .amount(BigDecimal.ZERO)
                                .build();
                    } else {
                        BigDecimal currentBillingPlan = apiPricingDbRecord().get(customerCurrentBillingPlanOption.get());
                        int perBillRatio = 0;
                        if (apiRequestCount < 1000 && apiRequestCount > 0) {
                            perBillRatio = 1;
                        } else {
                            perBillRatio = (int) (apiRequestCount / 1000); //request-per-1000
                            if ((apiRequestCount % 1000) > 0) {
                                perBillRatio += (int) Math.ceil(apiRequestCount % 1000);
                            }
                        }
                        reporterBuilder.amount(currentBillingPlan.multiply(BigDecimal.valueOf(perBillRatio)));
                    }
                    return reporterBuilder.build();
                }).map(ResponseEntity::ok);
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

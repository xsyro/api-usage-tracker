package com.xsyro.jamiu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties("exchange")
@Data
public class ExchangeBinding {
    private Map<String,QueueInfo> queues = new HashMap<>();
    private Map<String,QueueInfo> topics = new HashMap<>();


    @Data
    public static class QueueInfo {
        private String exchange;
        private String routingKey;
    }
}

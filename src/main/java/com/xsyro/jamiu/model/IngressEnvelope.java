package com.xsyro.jamiu.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class IngressEnvelope implements Serializable {
    @NotNull
    private Integer customerId;


    @NotNull
    private String scope;
    @Min(100)
    private Integer httpStatusCode;
    @URL
    private String endpoint;
    @NotNull
    private Long timestamp;
    private Long responseMillis;

    @Nullable
    private Map<String, Object> extras = new HashMap<>();
}

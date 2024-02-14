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
    private Long timestamp;
    @NotNull
    private Integer customerId;
    @NotNull
    private String serviceTag;
    @URL
    private String endpoint;
    @Nullable
    private Map<String, Object> extras = new HashMap<>();

    //NOT TO BE PROVIDED IN HTTP REQUEST-BODY
    //This is for trace purpose only
    private Long session;
}

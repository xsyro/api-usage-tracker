package com.xsyro.jamiu.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class Ingress {
    @Min(100)
    private Integer httpStatusCode;
    @URL
    private String endpoint;
    @NotNull
    private Long currentMills;
    private Long responseMillis;
    private String scope;
}

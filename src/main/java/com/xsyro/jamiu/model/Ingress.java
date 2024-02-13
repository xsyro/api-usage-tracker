package com.xsyro.jamiu.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

@Data
public class Ingress {
    @NotNull
    private Integer customerRef;

    @NotNull
    private String scope;
    @Min(100)
    private Integer httpStatusCode;
    @URL
    private String endpoint;
    @NotNull
    private Long timestamp;
    private Long responseMillis;
}

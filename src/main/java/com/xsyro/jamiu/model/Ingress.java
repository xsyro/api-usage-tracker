package com.xsyro.jamiu.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.util.Date;

import static java.lang.System.currentTimeMillis;

@Data
public class Ingress {
    @Min(100)
    private Integer httpStatusCode;
    @URL
    private String endpoint;
    @NotNull
    @NotBlank
    private Long currentMills;
    private Long responseMillis;
    private String scope;
}

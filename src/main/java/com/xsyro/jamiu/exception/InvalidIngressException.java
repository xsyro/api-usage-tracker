package com.xsyro.jamiu.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidIngressException extends Exception {

    public InvalidIngressException(String msg) {
        super(msg);
        log.error("We encountered error while validating the [IngressEnvelope].");
    }
}

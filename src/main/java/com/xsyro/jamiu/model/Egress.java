package com.xsyro.jamiu.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Egress {
    String code;
    Boolean isSuccessful;
    String msg;
    Long session;



    public static final String OK = "200"; //Ingress is received by Queue
    public static final String CREATED = "201"; //Ingress has being persisted to the DB
    public static final String ERROR_CODE_NOT_FOUND = "404"; //Queue not found for binding
}

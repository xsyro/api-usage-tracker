package com.xsyro.jamiu.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Egress {
    Boolean isSuccessful;
    String msg;
}

package com.xsyro.jamiu.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UsageActivityLog {
    private static final Long currentMillis = System.currentTimeMillis();

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;
    private Date createdAt = new Date(currentMillis);
    private Date updatedAt = new Date(currentMillis);
    private Date deletedAt;
    private Integer customerId;


    @Column(nullable = false)
    private String scope;
}

package com.kan.registry.health.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Document extends AbstractDomain{

    @Column
    private String description;

    @Column
    private String type;

    @Column
    private String path;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}

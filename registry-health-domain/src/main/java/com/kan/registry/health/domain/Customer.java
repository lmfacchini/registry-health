package com.kan.registry.health.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.time.LocalDate;

@Data
@Entity
@Table
@NoArgsConstructor
public class Customer extends AbstractDomain{

    public Customer(Long id) {
        setId(id);
    }

    @Column
    private String name;

    @Column
    private String phoneNumber;

    @Column
    private LocalDate birthDate;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Document> documents;
}

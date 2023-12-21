package com.kan.registry.health.dist.to;

import lombok.Data;

import java.util.*;
import java.time.LocalDate;

@Data
public class CustomerTO extends TransactionObject{

    private String name;

    private String phoneNumber;

    private LocalDate birthDate;

    private List<DocumentTO> documents;


}

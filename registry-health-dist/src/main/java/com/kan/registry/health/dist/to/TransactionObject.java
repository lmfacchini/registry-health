package com.kan.registry.health.dist.to;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class TransactionObject {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

package com.kan.registry.health.dist.service;

import com.kan.registry.health.dist.to.DocumentTO;

import java.util.Collection;


public interface DocumentService {
    Collection<DocumentTO> list(Long customerId);

}

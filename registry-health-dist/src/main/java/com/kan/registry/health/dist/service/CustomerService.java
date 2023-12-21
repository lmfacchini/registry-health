package com.kan.registry.health.dist.service;

import com.kan.registry.health.dist.to.CustomerTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    CustomerTO registry(CustomerTO customer);

    Page<CustomerTO> list(Pageable pageable);

    CustomerTO update(CustomerTO customer);

    void delete(Long id);
}

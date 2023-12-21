package com.kan.registry.health.resource.impl;

import com.kan.registry.health.dist.resource.CustomerResource;
import com.kan.registry.health.dist.service.CustomerService;
import com.kan.registry.health.dist.to.CustomerTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CustomerResourceImpl implements CustomerResource {

    private final CustomerService customerService;

    public CustomerResourceImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<CustomerTO> registry(CustomerTO customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.registry(customer));
    }

    @Override
    public ResponseEntity<Page<CustomerTO>> list(Pageable pageable) {
        Page<CustomerTO> page = customerService.list(pageable);
        return page.getSize() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(customerService.list(pageable));
    }

    @Override
    public ResponseEntity<CustomerTO> update(CustomerTO customer) {
        return ResponseEntity.ok(customerService.update(customer));
    }

    @Override
    public ResponseEntity delete(Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

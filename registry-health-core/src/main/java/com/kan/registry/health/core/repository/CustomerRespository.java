package com.kan.registry.health.core.repository;

import com.kan.registry.health.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRespository extends JpaRepository<Customer, Long> {
}

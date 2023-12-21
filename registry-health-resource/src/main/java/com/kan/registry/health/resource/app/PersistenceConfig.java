package com.kan.registry.health.resource.app;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.kan.registry.health.core.repository", "com.kan.registry.health.domain"})
@EntityScan(basePackages = "com.kan.registry.health.domain")
public class PersistenceConfig {
}

package com.kan.registry.health.core.repository;

import com.kan.registry.health.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface DocumentRepository extends JpaRepository<Document, Long> {


    @Query("SELECT e FROM Document e INNER JOIN e.customer c WHERE c.id = :customerId")
    Collection<Document> listByCustomerId(@Param("customerId") Long customerId);

}

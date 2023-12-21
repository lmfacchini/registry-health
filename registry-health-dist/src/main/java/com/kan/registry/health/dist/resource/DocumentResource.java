package com.kan.registry.health.dist.resource;

import com.kan.registry.health.dist.to.DocumentTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@RequestMapping("protected/document")
public interface DocumentResource {


    @GetMapping("{customerId}")
    ResponseEntity<Collection<DocumentTO>> list(@PathVariable("customerId") Long customerId);
}

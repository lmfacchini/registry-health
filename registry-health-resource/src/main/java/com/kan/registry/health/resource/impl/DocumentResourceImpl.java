package com.kan.registry.health.resource.impl;

import com.kan.registry.health.dist.resource.DocumentResource;
import com.kan.registry.health.dist.service.DocumentService;
import com.kan.registry.health.dist.to.DocumentTO;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class DocumentResourceImpl implements DocumentResource {

    private final DocumentService documentService;


    public DocumentResourceImpl(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public ResponseEntity<Collection<DocumentTO>> list(Long customerId) {
        Collection<DocumentTO> result = documentService.list(customerId);

        return CollectionUtils.isEmpty(result)?ResponseEntity.noContent().build():ResponseEntity.ok(result);
    }
}

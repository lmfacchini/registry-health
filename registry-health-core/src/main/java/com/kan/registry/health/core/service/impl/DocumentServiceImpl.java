package com.kan.registry.health.core.service.impl;

import com.kan.registry.health.core.repository.DocumentRepository;
import com.kan.registry.health.dist.exception.ServiceException;
import com.kan.registry.health.dist.service.DocumentService;
import com.kan.registry.health.dist.to.DocumentTO;
import com.kan.registry.health.domain.Customer;
import com.kan.registry.health.domain.Document;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@Service
public class DocumentServiceImpl extends AbstractService<DocumentTO, Document, DocumentRepository> implements DocumentService {


    public DocumentServiceImpl(DocumentRepository repository) {
        super(repository);
    }

    @Override
    public Collection<DocumentTO> list(Long customerId) {
        return parse(repository.listByCustomerId(customerId));
    }

    protected DocumentTO parse(Document document){
        DocumentTO to = super.parse(document);
        to.setDescription(document.getDescription());
        to.setType(document.getType());
        return to;
    }

    @Override
    protected Document parse(DocumentTO to) {
        Document domain = super.parse(to);
        domain.setCustomer(new Customer(to.getCustomerId()));
        domain.setDescription(to.getDescription());
        domain.setType(to.getType());
        return domain;
    }

    public DocumentTO save(DocumentTO to) {
        try{

            Document domain = parse(to);
            if(to.getId() == null){
                domain = create(domain);
                to.setNewContent(true);
            }else{
                domain = update(domain);
            }

            if(to.isNewContent()){

                Path path = Files.createTempFile(Long.toHexString(to.getId()), to.getType());

                try(InputStream input = new BufferedInputStream(to.getFile().getInputStream());
                    OutputStream output = new BufferedOutputStream(new FileOutputStream(path.toFile()))){

                    IOUtils.copy(input, output);
                }

                domain.setPath(path.toString());
                domain = repository.save(domain);
            }



            return parse(domain);
        }catch (Exception ex){
            throw new ServiceException(ex);
        }

    }
}

package com.kan.registry.health.core.service.impl;

import com.kan.registry.health.core.repository.CustomerRespository;
import com.kan.registry.health.dist.service.CustomerService;
import com.kan.registry.health.dist.to.CustomerTO;
import com.kan.registry.health.dist.to.DocumentTO;
import com.kan.registry.health.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl extends AbstractService<CustomerTO, Customer, CustomerRespository> implements CustomerService {



    @Autowired
    private DocumentServiceImpl documentService;

    public CustomerServiceImpl(CustomerRespository respository) {
        super(respository);
    }

    @Override
    public CustomerTO registry(CustomerTO to) {
        Customer domain = parse(to);
        final Customer saved = create(domain);
        to.getDocuments().forEach(doc->{
            doc.setCustomerId(saved.getId());
            documentService.save(doc);
        });

        return parse(saved);
    }

    @Override
    public Page<CustomerTO> list(Pageable pageable) {
        Page<Customer> page = repository.findAll(pageable);
        List<CustomerTO> data = page.stream().map(this::parse).collect(Collectors.toList());
        return new PageImpl(data, pageable, page.getTotalElements());

    }

    @Override
    public CustomerTO update(CustomerTO to) {
        final Customer domain = super.update(parse(to));
        to.getDocuments().forEach(doc->{
            doc.setCustomerId(domain.getId());
            documentService.save(doc);
        });
        return parse(domain);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    protected CustomerTO parse(Customer domain) {
        CustomerTO to = super.parse(domain);
        to.setBirthDate(domain.getBirthDate());
        to.setName(domain.getName());
        to.setDocuments(documentService.parse(domain.getDocuments()));
        to.setPhoneNumber(domain.getPhoneNumber());
        return to;
    }

    @Override
    protected Customer parse(CustomerTO to) {
        Customer domain = super.parse(to);
        domain.setBirthDate(to.getBirthDate());
        domain.setDocuments(documentService.inverse(to.getDocuments()));
        domain.setPhoneNumber(to.getPhoneNumber());
        domain.setName(to.getName());
        return domain;
    }
}

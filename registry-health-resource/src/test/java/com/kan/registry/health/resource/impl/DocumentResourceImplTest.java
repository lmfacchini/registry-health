package com.kan.registry.health.resource.impl;

import com.kan.registry.health.dist.resource.CustomerResource;
import com.kan.registry.health.dist.resource.DocumentResource;
import com.kan.registry.health.dist.service.DocumentService;
import com.kan.registry.health.dist.to.CustomerTO;
import com.kan.registry.health.dist.to.DocumentTO;
import com.kan.registry.health.resource.app.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
public class DocumentResourceImplTest{

    @Autowired
    private DocumentResource documentResource;

    @Autowired
    private CustomerResource customerResource;


    private CustomerTO registry() {

        CustomerTO customer = createMock();
        ResponseEntity<CustomerTO> response = customerResource.registry(customer);
        return response.getBody();

    }


    @Test
    public void listTest() {
        registry();
        ResponseEntity<Collection<DocumentTO>> response = documentResource.list(registry().getId());

        assertEquals("Response status", HttpStatus.OK,response.getStatusCode());
        assertNotNull("Result is null.", response.getBody());
        assertEquals("Total", 1,response.getBody().size());
    }


    private CustomerTO createMock() {
        CustomerTO to = new CustomerTO();
        to.setName("Novo Customer");
        to.setPhoneNumber("1111111111");
        to.setBirthDate(LocalDate.now());
        DocumentTO doc = new DocumentTO();
        doc.setDescription("Description");
        doc.setType("text");
        doc.setFile(new MockMultipartFile("teste", "Test teste teste".getBytes()));
        to.setDocuments(List.of(doc));
        return to;
    }
}

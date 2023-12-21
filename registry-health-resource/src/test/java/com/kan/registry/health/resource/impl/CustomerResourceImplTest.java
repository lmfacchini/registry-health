package com.kan.registry.health.resource.impl;

import com.kan.registry.health.dist.resource.CustomerResource;
import com.kan.registry.health.dist.to.CustomerTO;
import com.kan.registry.health.dist.to.DocumentTO;
import com.kan.registry.health.resource.app.Application;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import java.time.LocalDate;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
public class CustomerResourceImplTest {


    @Autowired
    private CustomerResource customerResource;

    @Test
    public void registryTest() {

        CustomerTO customer = createMock();
        ResponseEntity<CustomerTO> response = customerResource.registry(customer);
        assertEquals("Response Status", HttpStatus.CREATED,response.getStatusCode());
        assertNotNull("Result customer is null.", response.getBody());
    }


    @Test
    public void listTest() {
        CustomerTO customer = createMock();
        customerResource.registry(customer);
        ResponseEntity<Page<CustomerTO>> response = customerResource.list(Pageable.unpaged());
        assertEquals("Response Status", HttpStatus.OK,response.getStatusCode());
        assertNotNull("Result is null.", response.getBody());
        assertEquals("Total", 3,(int)response.getBody().getTotalElements());
    }

    @Test
    public void updateTest() {
        CustomerTO customer = createMock();
        ResponseEntity<CustomerTO> response = customerResource.registry(customer);
        response.getBody().setName("Alterado");
        response = customerResource.update(response.getBody());
        assertEquals("Response Status", HttpStatus.OK,response.getStatusCode());
        assertNotNull("Result is null.", response.getBody());
        assertEquals("Update", "Alterado",response.getBody().getName());
    }


    @Test
    public void deleteTest() {
        CustomerTO customer = createMock();
        ResponseEntity<CustomerTO> response = customerResource.registry(customer);
        assertEquals("Response Status", HttpStatus.CREATED,response.getStatusCode());
        ResponseEntity<Page<CustomerTO>> responsePage = customerResource.list(Pageable.unpaged());
        assertEquals("Response Status", HttpStatus.OK,responsePage.getStatusCode());
        assertEquals("Total", (int)responsePage.getBody().getTotalElements(), 4);
        customerResource.delete(response.getBody().getId());
        responsePage = customerResource.list(Pageable.unpaged());

        assertEquals("Total", (int)responsePage.getBody().getTotalElements(), 3);
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

package com.kan.registry.health.dist.resource;

import com.kan.registry.health.dist.to.CustomerTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("protected/customer")
public interface CustomerResource {


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<CustomerTO> registry(@ModelAttribute CustomerTO customer);

    @GetMapping
    ResponseEntity<Page<CustomerTO>> list(@PageableDefault Pageable pageable);


    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<CustomerTO> update(@ModelAttribute CustomerTO customer);

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    ResponseEntity delete(@PathVariable("id") Long id);

}

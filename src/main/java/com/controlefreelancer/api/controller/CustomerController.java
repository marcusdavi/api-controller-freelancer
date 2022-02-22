package com.controlefreelancer.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.controlefreelancer.api.domain.model.CustomerModel;
import com.controlefreelancer.api.dto.CustomerDto;
import com.controlefreelancer.api.event.ResourceCreatedEvent;
import com.controlefreelancer.api.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    private final ApplicationEventPublisher publisher;

    public CustomerController(CustomerService service, ApplicationEventPublisher publisher) {
	this.service = service;
	this.publisher = publisher;
    }

    @GetMapping
    public ResponseEntity<List<CustomerModel>> list() {
	return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerModel> get(@PathVariable Integer id) {
	return service.get(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CustomerModel>> findByCommercialNameAndCnpj(@RequestParam(required = false) String commercialName, @RequestParam(required = false) String cnpj, @PageableDefault(page = 0, size = 10, sort = "commercialName", direction = Sort.Direction.ASC) Pageable pageable) {
	return ResponseEntity.ok(service.findByCommercialNameAndCnpj(commercialName, cnpj, pageable));
    }

    @PostMapping
    public ResponseEntity<CustomerModel> create(@Valid @RequestBody CustomerDto category,
	    HttpServletResponse response) {

	CustomerModel newCategory = service.create(category);

	publisher.publishEvent(new ResourceCreatedEvent(this, response, newCategory.getId()));

	return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @PutMapping("/{id}/archives")
    public ResponseEntity<Void> archive(@PathVariable Integer id) {

	service.archive(id);
	return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerModel> update(@PathVariable Integer id, @RequestBody @Valid CustomerDto category) {

	CustomerModel categoryUpdate = service.update(id, category);

	return ResponseEntity.ok(categoryUpdate);

    }
}

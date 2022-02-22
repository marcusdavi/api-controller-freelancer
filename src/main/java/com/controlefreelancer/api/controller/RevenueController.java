	package com.controlefreelancer.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controlefreelancer.api.domain.model.RevenueModel;
import com.controlefreelancer.api.dto.RevenueDto;
import com.controlefreelancer.api.event.ResourceCreatedEvent;
import com.controlefreelancer.api.service.RevenueService;

@RestController
@RequestMapping("/revenues")
public class RevenueController {

    private final RevenueService service;
    private final ApplicationEventPublisher publisher;

    public RevenueController(RevenueService service, ApplicationEventPublisher publisher) {
	this.service = service;
	this.publisher = publisher;
    }

    @GetMapping
    public ResponseEntity<List<RevenueModel>> list() {
	return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RevenueModel> get(@PathVariable Integer id) {
	return service.get(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RevenueModel> create(@Valid @RequestBody RevenueDto revenue, HttpServletResponse response) {

	RevenueModel newRevenue = service.create(revenue);

	publisher.publishEvent(new ResourceCreatedEvent(this, response, newRevenue.getId()));

	return ResponseEntity.status(HttpStatus.CREATED).body(newRevenue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RevenueModel> update(@PathVariable Integer id, @RequestBody @Valid RevenueDto Revenue) {

	RevenueModel RevenueUpdate = service.update(id, Revenue);

	return ResponseEntity.ok(RevenueUpdate);

    }    
}

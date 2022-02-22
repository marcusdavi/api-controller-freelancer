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

import com.controlefreelancer.api.domain.model.ExpenseModel;
import com.controlefreelancer.api.dto.ExpenseDto;
import com.controlefreelancer.api.event.ResourceCreatedEvent;
import com.controlefreelancer.api.service.ExpenseService;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService service;
    private final ApplicationEventPublisher publisher;

    public ExpenseController(ExpenseService service, ApplicationEventPublisher publisher) {
	this.service = service;
	this.publisher = publisher;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseModel>> list() {
	return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseModel> get(@PathVariable Integer id) {
	return service.get(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ExpenseModel> create(@Valid @RequestBody ExpenseDto expense, HttpServletResponse response) {

	ExpenseModel newExpense = service.create(expense);

	publisher.publishEvent(new ResourceCreatedEvent(this, response, newExpense.getId()));

	return ResponseEntity.status(HttpStatus.CREATED).body(newExpense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseModel> update(@PathVariable Integer id, @RequestBody @Valid ExpenseDto Expense) {

	ExpenseModel ExpenseUpdate = service.update(id, Expense);

	return ResponseEntity.ok(ExpenseUpdate);

    }    
}

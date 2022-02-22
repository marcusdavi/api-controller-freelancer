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

import com.controlefreelancer.api.domain.model.CategoryModel;
import com.controlefreelancer.api.dto.CategoryDto;
import com.controlefreelancer.api.event.ResourceCreatedEvent;
import com.controlefreelancer.api.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    private final ApplicationEventPublisher publisher;

    public CategoryController(CategoryService service, ApplicationEventPublisher publisher) {
	super();
	this.service = service;
	this.publisher = publisher;
    }

    @GetMapping
    public ResponseEntity<List<CategoryModel>> list() {
	return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryModel> get(@PathVariable Integer id) {
	return service.get(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CategoryModel>> findByName(@RequestParam String name, @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
	return ResponseEntity.ok(service.findByName(name, pageable));
    }

    @PostMapping
    public ResponseEntity<CategoryModel> create(@Valid @RequestBody CategoryDto category,
	    HttpServletResponse response) {

	CategoryModel newCategory = service.create(category);

	publisher.publishEvent(new ResourceCreatedEvent(this, response, newCategory.getId()));

	return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @PutMapping("/{id}/archives")
    public ResponseEntity<Void> archive(@PathVariable Integer id) {

	service.archive(id);
	return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryModel> update(@PathVariable Integer id, @RequestBody @Valid CategoryDto category) {

	CategoryModel categoryUpdate = service.update(id, category);

	return ResponseEntity.ok(categoryUpdate);

    }
}

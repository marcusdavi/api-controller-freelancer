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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.controlefreelancer.api.dto.UserDto;
import com.controlefreelancer.api.dto.response.UserResponseDto;
import com.controlefreelancer.api.event.ResourceCreatedEvent;
import com.controlefreelancer.api.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final ApplicationEventPublisher publisher;

    public UserController(UserService service, ApplicationEventPublisher publisher) {
	this.service = service;
	this.publisher = publisher;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> list() {
	return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> get(@PathVariable Integer id) {
	return service.get(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDto>> findByName(@RequestParam String name) {
	return ResponseEntity.ok(service.findByName(name));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserDto User, HttpServletResponse response) {

	UserResponseDto newUser = service.create(User);

	publisher.publishEvent(new ResourceCreatedEvent(this, response, newUser.getId()));

	return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Integer id, @RequestBody @Valid UserDto User) {

	UserResponseDto UserUpdate = service.update(id, User);

	return ResponseEntity.ok(UserUpdate);

    }    
}

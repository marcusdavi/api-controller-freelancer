package com.controlefreelancer.api.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controlefreelancer.api.domain.model.SettingsModel;
import com.controlefreelancer.api.dto.SettingsDto;
import com.controlefreelancer.api.service.SettingsService;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final SettingsService service;

    public SettingsController(SettingsService service) {
	this.service = service;
    }

    @GetMapping
    public ResponseEntity<SettingsModel> settings() {
	return ResponseEntity.ok(service.getSettings());
    }
    
    @PutMapping
    public ResponseEntity<SettingsModel> settings(@RequestBody @Valid SettingsDto settings) {
	return ResponseEntity.ok(service.update(settings));
    }

}

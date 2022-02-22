package com.controlefreelancer.api.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controlefreelancer.api.dto.ReportTotalDto;
import com.controlefreelancer.api.dto.response.ReportByMonthResponseDto;
import com.controlefreelancer.api.dto.response.ReportTotalResponseDto;
import com.controlefreelancer.api.service.ReportsService;

@RestController
@RequestMapping("/reports")
public class ReportsController {

    private final ReportsService service;

    public ReportsController(ReportsService service) {
	this.service = service;
    }

    @PostMapping("/total-revenue")
    public ResponseEntity<ReportTotalResponseDto> getTotal(@RequestBody @Valid ReportTotalDto reportTotalDto) {
	return ResponseEntity.ok(service.getTotal(reportTotalDto.getFiscalYear()));
    }
    
    @PostMapping("/revenue-by-month")
    public ResponseEntity<ReportByMonthResponseDto> getByMonth(@RequestBody @Valid ReportTotalDto reportTotalDto) {
	return ResponseEntity.ok(service.getMonth(reportTotalDto.getFiscalYear()));
    }
}

package com.controlefreelancer.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.controlefreelancer.api.dto.ReportTotalDto;
import com.controlefreelancer.api.dto.response.ReportByMonthResponseDto;
import com.controlefreelancer.api.dto.response.ReportTotalResponseDto;
import com.controlefreelancer.api.service.ReportsService;

@RunWith(MockitoJUnitRunner.class)
public class ReportsControllerTest {

    @InjectMocks
    private ReportsController controller;

    @Mock
    private ReportsService service;

    @Test
    public void testGetTotalOk() {
	ReportTotalDto reportTotalDto = new ReportTotalDto();

	ReportTotalResponseDto reportTotalResponseDto = new ReportTotalResponseDto(BigDecimal.TEN, BigDecimal.ONE);

	when(service.getTotal(Mockito.any())).thenReturn(reportTotalResponseDto);

	ReportTotalResponseDto response = controller.getTotal(reportTotalDto).getBody();

	assertEquals(BigDecimal.ONE, response.getMaxRevenueAmount());
	assertEquals(BigDecimal.TEN, response.getTotalRevenue());
    }

    @Test
    public void testGetByMonthOk() {
	ReportTotalDto reportTotalDto = new ReportTotalDto();

	ReportByMonthResponseDto reportByMonthResponseDto = new ReportByMonthResponseDto(new ArrayList<>(),
		BigDecimal.ONE);

	when(service.getMonth(Mockito.any())).thenReturn(reportByMonthResponseDto);

	ReportByMonthResponseDto response = controller.getByMonth(reportTotalDto).getBody();

	assertEquals(BigDecimal.ONE, response.getMaxRevenueAmount());
	assertTrue(response.getMonthsRevenue().isEmpty());
    }

}

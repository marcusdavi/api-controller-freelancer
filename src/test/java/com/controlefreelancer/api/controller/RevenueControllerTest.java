package com.controlefreelancer.api.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.controlefreelancer.api.domain.model.RevenueModel;
import com.controlefreelancer.api.dto.RevenueDto;
import com.controlefreelancer.api.service.RevenueService;

@RunWith(MockitoJUnitRunner.class)
public class RevenueControllerTest {

    private static final String REVENUE_01 = "revenue 01";
    private static final String REVENUE_02 = "revenue 02";

    @InjectMocks
    private RevenueController controller;

    @Mock
    private RevenueService service;

    @Mock
    private ApplicationEventPublisher publisher;

    @Test
    public void testListOk() {

	List<RevenueModel> revenues = new ArrayList<>();
	revenues.add(buildRevenueModel(BigDecimal.TEN, REVENUE_01));
	revenues.add(buildRevenueModel(BigDecimal.ONE, REVENUE_02));

	when(service.list()).thenReturn(revenues);

	ResponseEntity<List<RevenueModel>> responseEntity = controller.list();
	List<RevenueModel> response = responseEntity.getBody();

	assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	assertTrue(!response.isEmpty());
	assertEquals(2, response.size());
	assertEquals(BigDecimal.TEN, response.get(0).getAmount());
	assertEquals(REVENUE_01, response.get(0).getDescription());
	assertEquals(BigDecimal.ONE, response.get(1).getAmount());
	assertEquals(REVENUE_02, response.get(1).getDescription());

    }

    @Test
    public void testListEmptyOk() {

	when(service.list()).thenReturn(new ArrayList<>());

	List<RevenueModel> response = controller.list().getBody();

	assertTrue(response.isEmpty());

    }

    @Test
    public void testGetRevenueOk() {

	when(service.get(Mockito.anyInt())).thenReturn(Optional.of(buildRevenueModel(BigDecimal.TEN, REVENUE_01)));

	ResponseEntity<RevenueModel> responseEntity = controller.get(1);

	RevenueModel response = responseEntity.getBody();

	assertNotNull(response);
	assertEquals(BigDecimal.TEN, response.getAmount());
	assertEquals(REVENUE_01, response.getDescription());
	assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    public void testGetRevenuenotFound() {

	when(service.get(Mockito.anyInt())).thenReturn(Optional.empty());

	ResponseEntity<RevenueModel> response = controller.get(1);

	assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	assertNull(response.getBody());

    }

    @Test
    public void testCreateRevenueOk() {

	RevenueModel revenue = buildRevenueModel(BigDecimal.TEN, REVENUE_01);
	RevenueDto revenueDto = buildRevenueDto(BigDecimal.TEN, REVENUE_01);

	when(service.create(Mockito.any())).thenReturn(revenue);

	ResponseEntity<RevenueModel> responseEntity = controller.create(revenueDto, null);

	RevenueModel response = responseEntity.getBody();

	assertNotNull(response);
	assertEquals(BigDecimal.TEN, response.getAmount());
	assertEquals(REVENUE_01, response.getDescription());
	assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateOk() {

	RevenueModel revenue = buildRevenueModel(BigDecimal.TEN, REVENUE_01);
	RevenueDto revenueDto = buildRevenueDto(BigDecimal.TEN, REVENUE_01);

	when(service.update(Mockito.anyInt(), Mockito.any())).thenReturn(revenue);

	ResponseEntity<RevenueModel> responseEntity = controller.update(1, revenueDto);

	RevenueModel response = responseEntity.getBody();

	assertNotNull(response);
	assertEquals(BigDecimal.TEN, response.getAmount());
	assertEquals(REVENUE_01, response.getDescription());
	assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private RevenueModel buildRevenueModel(BigDecimal amount, String description) {
	RevenueModel revenue = new RevenueModel();
	revenue.setAmount(amount);
	revenue.setDescription(description);
	return revenue;
    }

    private RevenueDto buildRevenueDto(BigDecimal amount, String description) {
	RevenueDto revenueDto = new RevenueDto();
	revenueDto.setDescription(description);
	revenueDto.setAmount(amount);
	return revenueDto;
    }
}

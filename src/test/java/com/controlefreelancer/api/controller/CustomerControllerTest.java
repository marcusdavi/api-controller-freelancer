package com.controlefreelancer.api.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.controlefreelancer.api.domain.model.CustomerModel;
import com.controlefreelancer.api.dto.CustomerDto;
import com.controlefreelancer.api.service.CustomerService;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController controller;

    @Mock
    private CustomerService service;

    @Mock
    private ApplicationEventPublisher publisher;

    @Test
    public void testListOk() {

	List<CustomerModel> customers = new ArrayList<>();
	customers.add(buildCustomerModel(1, "customer 01", "customer 01 - test"));
	customers.add(buildCustomerModel(2, "customer 02", "customer 02 - test"));

	when(service.list()).thenReturn(customers);

	List<CustomerModel> response = controller.list().getBody();

	assertTrue(!response.isEmpty());
	assertEquals(2, response.size());
	assertEquals(1, response.get(0).getId());
	assertEquals("customer 01", response.get(0).getLegalName());
	assertEquals(2, response.get(1).getId());
	assertEquals("customer 02", response.get(1).getLegalName());

    }

    @Test
    public void testListEmptyOk() {

	when(service.list()).thenReturn(new ArrayList<>());

	List<CustomerModel> response = controller.list().getBody();

	assertTrue(response.isEmpty());

    }

    @Test
    public void testGetCustomerOk() {

	when(service.get(Mockito.anyInt())).thenReturn(Optional.of(buildCustomerModel(1, "Vibbra SA", "Vibbra")));

	ResponseEntity<CustomerModel> response = controller.get(1);

	assertNotNull(response.getBody());
	assertEquals(1, response.getBody().getId());
	assertEquals("Vibbra SA", response.getBody().getLegalName());
	assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testGetCustomernotFound() {

	when(service.get(Mockito.anyInt())).thenReturn(Optional.empty());

	ResponseEntity<CustomerModel> response = controller.get(1);

	assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	assertNull(response.getBody());

    }

    @Test
    public void testGetCustomerByCommercialName() {

	List<CustomerModel> customers = new ArrayList<>();
	customers.add(buildCustomerModel(1, "Marcio SA", "Marcio"));
	customers.add(buildCustomerModel(2, "Marcus SA", "Marcus"));

	PageRequest page = PageRequest.of(0, 10);

	Page<CustomerModel> customersPage = new PageImpl<>(customers, page, customers.size());
	when(service.findByCommercialNameAndCnpj(Mockito.anyString(), Mockito.anyString(), Mockito.any()))
		.thenReturn(customersPage);

	Page<CustomerModel> response = controller.findByCommercialNameAndCnpj("MAR", "11111111111", page).getBody();

	assertTrue(!response.isEmpty());
	assertEquals(2L, response.getTotalElements());
	assertEquals(1, response.get().findFirst().get().getId());
	assertEquals("Marcio SA", response.get().findFirst().get().getLegalName());

    }

    @Test
    public void testCreateCustomerOk() {

	CustomerDto customerDto = buildCustomerDto("Customer 01 SA", "Customer 01");
	CustomerModel customer = buildCustomerModel(1, customerDto.getLegalName(), customerDto.getCommercialName());

	when(service.create(Mockito.any())).thenReturn(customer);

	ResponseEntity<CustomerModel> response = controller.create(customerDto, null);

	assertNotNull(response.getBody());
	assertEquals(1, response.getBody().getId());
	assertEquals("Customer 01 SA", response.getBody().getLegalName());
	assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testDeleteOk() {

	ResponseEntity<Void> response = controller.archive(1);

	assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testUpdateOk() {

	CustomerDto customerDto = buildCustomerDto("Customer 01 SA", "Customer 01");
	CustomerModel customer = buildCustomerModel(1, customerDto.getLegalName(), customerDto.getCommercialName());

	when(service.update(Mockito.anyInt(), Mockito.any())).thenReturn(customer);

	ResponseEntity<CustomerModel> response = controller.update(1, customerDto);

	assertNotNull(response.getBody());
	assertEquals(1, response.getBody().getId());
	assertEquals("Customer 01 SA", response.getBody().getLegalName());
	assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private CustomerModel buildCustomerModel(Integer id, String legalName, String commercialName) {
	CustomerModel customer = new CustomerModel();
	customer.setId(id);
	customer.setLegalName(legalName);
	customer.setCommercialName(commercialName);
	return customer;
    }

    private CustomerDto buildCustomerDto(String legalName, String commercialName) {
	return CustomerDto.builder().legalName(legalName).commercialName(commercialName).build();
    }
}

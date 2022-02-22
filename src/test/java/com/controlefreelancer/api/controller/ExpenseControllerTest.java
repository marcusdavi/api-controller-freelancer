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

import com.controlefreelancer.api.domain.model.ExpenseModel;
import com.controlefreelancer.api.dto.ExpenseDto;
import com.controlefreelancer.api.service.ExpenseService;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseControllerTest {

    private static final String EXPENSE_01 = "expense 01";
    private static final String EXPENSE_02 = "expense 02";

    @InjectMocks
    private ExpenseController controller;

    @Mock
    private ExpenseService service;

    @Mock
    private ApplicationEventPublisher publisher;

    @Test
    public void testListOk() {

	List<ExpenseModel> expenses = new ArrayList<>();
	expenses.add(buildExpenseModel(BigDecimal.TEN, EXPENSE_01));
	expenses.add(buildExpenseModel(BigDecimal.ONE, EXPENSE_02));

	when(service.list()).thenReturn(expenses);

	ResponseEntity<List<ExpenseModel>> responseEntity = controller.list();
	List<ExpenseModel> response = responseEntity.getBody();

	assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	assertTrue(!response.isEmpty());
	assertEquals(2, response.size());
	assertEquals(BigDecimal.TEN, response.get(0).getAmount());
	assertEquals(EXPENSE_01, response.get(0).getDescription());
	assertEquals(BigDecimal.ONE, response.get(1).getAmount());
	assertEquals(EXPENSE_02, response.get(1).getDescription());

    }

    @Test
    public void testListEmptyOk() {

	when(service.list()).thenReturn(new ArrayList<>());

	List<ExpenseModel> response = controller.list().getBody();

	assertTrue(response.isEmpty());

    }

    @Test
    public void testGetExpenseOk() {

	when(service.get(Mockito.anyInt())).thenReturn(Optional.of(buildExpenseModel(BigDecimal.TEN, EXPENSE_01)));

	ResponseEntity<ExpenseModel> responseEntity = controller.get(1);

	ExpenseModel response = responseEntity.getBody();

	assertNotNull(response);
	assertEquals(BigDecimal.TEN, response.getAmount());
	assertEquals(EXPENSE_01, response.getDescription());
	assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    public void testGetExpensenotFound() {

	when(service.get(Mockito.anyInt())).thenReturn(Optional.empty());

	ResponseEntity<ExpenseModel> response = controller.get(1);

	assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	assertNull(response.getBody());

    }

    @Test
    public void testCreateExpenseOk() {

	ExpenseModel expense = buildExpenseModel(BigDecimal.TEN, EXPENSE_01);
	ExpenseDto expenseDto = buildExpenseDto(BigDecimal.TEN, EXPENSE_01);

	when(service.create(Mockito.any())).thenReturn(expense);

	ResponseEntity<ExpenseModel> responseEntity = controller.create(expenseDto, null);

	ExpenseModel response = responseEntity.getBody();

	assertNotNull(response);
	assertEquals(BigDecimal.TEN, response.getAmount());
	assertEquals(EXPENSE_01, response.getDescription());
	assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateOk() {

	ExpenseModel expense = buildExpenseModel(BigDecimal.TEN, EXPENSE_01);
	ExpenseDto expenseDto = buildExpenseDto(BigDecimal.TEN, EXPENSE_01);

	when(service.update(Mockito.anyInt(), Mockito.any())).thenReturn(expense);

	ResponseEntity<ExpenseModel> responseEntity = controller.update(1, expenseDto);

	ExpenseModel response = responseEntity.getBody();

	assertNotNull(response);
	assertEquals(BigDecimal.TEN, response.getAmount());
	assertEquals(EXPENSE_01, response.getDescription());
	assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private ExpenseModel buildExpenseModel(BigDecimal amount, String description) {
	ExpenseModel expense = new ExpenseModel();
	expense.setAmount(amount);
	expense.setDescription(description);
	return expense;
    }

    private ExpenseDto buildExpenseDto(BigDecimal amount, String description) {
	ExpenseDto expenseDto = new ExpenseDto();
	expenseDto.setDescription(description);
	expenseDto.setAmount(amount);
	return expenseDto;
    }
}

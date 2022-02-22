package com.controlefreelancer.api.service.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.controlefreelancer.api.domain.model.Accrual;
import com.controlefreelancer.api.domain.model.CategoryModel;
import com.controlefreelancer.api.domain.model.CustomerModel;
import com.controlefreelancer.api.domain.model.ExpenseModel;
import com.controlefreelancer.api.domain.repository.CategoryRepository;
import com.controlefreelancer.api.domain.repository.CustomerRepository;
import com.controlefreelancer.api.domain.repository.ExpenseRepository;
import com.controlefreelancer.api.dto.ExpenseDto;
import com.controlefreelancer.api.exception.NegocioException;
import com.controlefreelancer.api.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final MessageSource messageSource;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, CustomerRepository customerRepository,
	    MessageSource messageSource, CategoryRepository categoryRepository) {
	super();
	this.expenseRepository = expenseRepository;
	this.customerRepository = customerRepository;
	this.categoryRepository = categoryRepository;
	this.messageSource = messageSource;
    }

    @Override
    public List<ExpenseModel> list() {
	return expenseRepository.findAll();
    }

    @Override
    public Optional<ExpenseModel> get(Integer id) {
	return expenseRepository.findById(id);
    }

    @Override
    public ExpenseModel create(@Valid ExpenseDto expenseDto) {
	ExpenseModel expenseModel = new ExpenseModel();
	BeanUtils.copyProperties(expenseDto, expenseModel);
	expenseModel.setAccrualDate(Accrual.fromInteger6(expenseDto.getAccrualDate()));

	CustomerModel customer = customerRepository.findById(expenseDto.getIdCustomer())
		.orElseThrow(() -> new NegocioException(
			messageSource.getMessage("expense-customer-not-found", null, LocaleContextHolder.getLocale())));
	expenseModel.setCustomer(customer);

	CategoryModel category = categoryRepository.findById(expenseDto.getIdCustomer())
		.orElseThrow(() -> new NegocioException(messageSource.getMessage("expense-category-not-exists", null,
			LocaleContextHolder.getLocale())));
	expenseModel.setCustomer(customer);
	expenseModel.setCategory(category);

	return expenseRepository.save(expenseModel);
    }

    @Override
    public ExpenseModel update(Integer id, @Valid ExpenseDto expenseDto) {
	ExpenseModel expenseModel = new ExpenseModel();
	BeanUtils.copyProperties(expenseDto, expenseModel);
	expenseModel.setAccrualDate(Accrual.fromInteger6(expenseDto.getAccrualDate()));

	CustomerModel customer = customerRepository.findById(expenseDto.getIdCustomer())
		.orElseThrow(() -> new NegocioException(
			messageSource.getMessage("expense-customer-not-found", null, LocaleContextHolder.getLocale())));
	expenseModel.setCustomer(customer);

	CategoryModel category = categoryRepository.findById(expenseDto.getIdCustomer())
		.orElseThrow(() -> new NegocioException(messageSource.getMessage("expense-category-not-exists", null,
			LocaleContextHolder.getLocale())));
	expenseModel.setCustomer(customer);
	expenseModel.setCategory(category);
	expenseModel.setId(id);

	expenseModel.setId(id);

	return expenseRepository.save(expenseModel);
    }

}

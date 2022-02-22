package com.controlefreelancer.api.service.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.controlefreelancer.api.domain.model.CustomerModel;
import com.controlefreelancer.api.domain.repository.CustomerRepository;
import com.controlefreelancer.api.dto.CustomerDto;
import com.controlefreelancer.api.exception.NegocioException;
import com.controlefreelancer.api.exception.ResourceNotFoundException;
import com.controlefreelancer.api.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final MessageSource messageSource;

    public CustomerServiceImpl(MessageSource messageSource, CustomerRepository customerRepository) {
	this.messageSource = messageSource;
	this.customerRepository = customerRepository;
    }

    @Override
    public Page<CustomerModel> findByCommercialNameAndCnpj(String commercialName, String cnpj, Pageable pageable) {
	if (!StringUtils.isEmpty(commercialName) && StringUtils.isEmpty(cnpj)) {
	    return customerRepository.findByCommercialNameContainsIgnoreCase(commercialName, pageable);
	} else if (StringUtils.isEmpty(commercialName) && !StringUtils.isEmpty(cnpj)) {
	    return customerRepository.findByCnpj(cnpj, pageable);
	} else if (!StringUtils.isEmpty(commercialName) && !StringUtils.isEmpty(cnpj)) {
	    return customerRepository.findByCommercialNameContainsIgnoreCaseAndCnpj(commercialName, cnpj, pageable);
	} else {
	    throw new NegocioException("Enter your commercial name or CNPJ.");
	}
    }

    public List<CustomerModel> list() {
	return customerRepository.findAll();
    }

    public Optional<CustomerModel> get(Integer id) {
	return customerRepository.findById(id);
    }

    public CustomerModel create(CustomerDto customer) {
	CustomerModel CustomerModel = new CustomerModel();
	BeanUtils.copyProperties(customer, CustomerModel);
	CustomerModel.setActive(true);
	return customerRepository.save(CustomerModel);

    }

    public void archive(Integer id) {

	CustomerModel customerUpdate = validExistenceCustomer(id);
	customerUpdate.setActive(false);
	customerRepository.save(customerUpdate);

    }

    private CustomerModel validExistenceCustomer(Integer id) {
	return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
		messageSource.getMessage("customer-not-found", null, LocaleContextHolder.getLocale())));
    }

    public CustomerModel update(Integer id, @Valid CustomerDto customer) {

	CustomerModel customerUpdate = validExistenceCustomer(id);
	BeanUtils.copyProperties(customer, customerUpdate, "id");
	return customerRepository.save(customerUpdate);
    }

}

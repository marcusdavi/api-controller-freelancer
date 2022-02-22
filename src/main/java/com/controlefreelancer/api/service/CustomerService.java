package com.controlefreelancer.api.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.controlefreelancer.api.domain.model.CustomerModel;
import com.controlefreelancer.api.dto.CustomerDto;

public interface CustomerService {

    List<CustomerModel> list();

    Optional<CustomerModel> get(Integer id);

    CustomerModel create(@Valid CustomerDto costumer);

    CustomerModel update(Integer id, @Valid CustomerDto costumer);
    
    Page<CustomerModel> findByCommercialNameAndCnpj(String commercialName, String cnpj, Pageable pageable);

    void archive(Integer id);
}
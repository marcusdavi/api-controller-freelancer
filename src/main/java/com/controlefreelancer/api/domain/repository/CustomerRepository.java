package com.controlefreelancer.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.controlefreelancer.api.domain.model.CustomerModel;

public interface CustomerRepository extends JpaRepository<CustomerModel, Integer> {

    Page<CustomerModel> findByCommercialNameContainsIgnoreCase(String name, Pageable pageable);

    Page<CustomerModel> findByCnpj(String cnpj, Pageable pageable);

    Page<CustomerModel> findByCommercialNameContainsIgnoreCaseAndCnpj(String commercialName, String cnpj, Pageable pageable);

}

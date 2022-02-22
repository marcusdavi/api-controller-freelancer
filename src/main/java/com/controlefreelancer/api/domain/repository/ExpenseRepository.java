package com.controlefreelancer.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.controlefreelancer.api.domain.model.ExpenseModel;

public interface ExpenseRepository extends JpaRepository<ExpenseModel, Integer> {

}

package com.controlefreelancer.api.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.controlefreelancer.api.domain.model.ExpenseModel;
import com.controlefreelancer.api.dto.ExpenseDto;

public interface ExpenseService {

    List<ExpenseModel> list();

    Optional<ExpenseModel> get(Integer id);

    ExpenseModel create(@Valid ExpenseDto expenseDto);

    ExpenseModel update(Integer id, @Valid ExpenseDto expenseDto);

}

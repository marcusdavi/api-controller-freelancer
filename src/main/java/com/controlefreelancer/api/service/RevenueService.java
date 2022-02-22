package com.controlefreelancer.api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.controlefreelancer.api.domain.model.RevenueModel;
import com.controlefreelancer.api.dto.RevenueDto;
import com.controlefreelancer.api.dto.response.ReporthRevenueMonthDto;

public interface RevenueService {

    List<RevenueModel> list();

    Optional<RevenueModel> get(Integer id);

    RevenueModel create(@Valid RevenueDto revenueDto);

    RevenueModel update(Integer id, @Valid RevenueDto revenueDto);

    BigDecimal findByFiscalYear(Integer fiscalYear);

    List<ReporthRevenueMonthDto> findByMonth(Integer fiscalYear);

}

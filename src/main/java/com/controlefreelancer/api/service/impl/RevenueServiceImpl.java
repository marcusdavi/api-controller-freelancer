package com.controlefreelancer.api.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.controlefreelancer.api.domain.model.Accrual;
import com.controlefreelancer.api.domain.model.CustomerModel;
import com.controlefreelancer.api.domain.model.RevenueModel;
import com.controlefreelancer.api.domain.projections.ReporthRevenueProjection;
import com.controlefreelancer.api.domain.repository.CustomerRepository;
import com.controlefreelancer.api.domain.repository.RevenueRepository;
import com.controlefreelancer.api.dto.RevenueDto;
import com.controlefreelancer.api.dto.response.ReporthRevenueMonthDto;
import com.controlefreelancer.api.exception.NegocioException;
import com.controlefreelancer.api.service.RevenueService;

@Service
public class RevenueServiceImpl implements RevenueService {

    private final RevenueRepository revenueRepository;
    private final CustomerRepository customerRepository;
    private final MessageSource messageSource;

    public RevenueServiceImpl(RevenueRepository revenueRepository, CustomerRepository customerRepository,
	    MessageSource messageSource) {
	this.revenueRepository = revenueRepository;
	this.customerRepository = customerRepository;
	this.messageSource = messageSource;
    }

    @Override
    public List<RevenueModel> list() {
	return revenueRepository.findAll();
    }

    @Override
    public Optional<RevenueModel> get(Integer id) {
	return revenueRepository.findById(id);
    }

    @Override
    public RevenueModel create(@Valid RevenueDto revenueDto) {
	RevenueModel revenueModel = new RevenueModel();
	BeanUtils.copyProperties(revenueDto, revenueModel);
	revenueModel.setAccrualDate(Accrual.fromInteger6(revenueDto.getAccrualDate()));

	CustomerModel customer = customerRepository.findById(revenueDto.getIdCustomer())
		.orElseThrow(() -> new NegocioException(
			messageSource.getMessage("revenue-customer-not-found", null, LocaleContextHolder.getLocale())));
	revenueModel.setCustomer(customer);

	return revenueRepository.save(revenueModel);
    }

    @Override
    public RevenueModel update(Integer id, @Valid RevenueDto revenueDto) {
	RevenueModel revenueModel = new RevenueModel();
	BeanUtils.copyProperties(revenueDto, revenueModel);
	revenueModel.setAccrualDate(Accrual.fromInteger6(revenueDto.getAccrualDate()));

	CustomerModel customer = customerRepository.findById(revenueDto.getIdCustomer())
		.orElseThrow(() -> new NegocioException(
			messageSource.getMessage("revenue-customer-not-found", null, LocaleContextHolder.getLocale())));
	revenueModel.setCustomer(customer);
	revenueModel.setId(id);

	return revenueRepository.save(revenueModel);
    }

    @Override
    public BigDecimal findByFiscalYear(Integer fiscalYear) {
	LocalDate starts = LocalDate.of(fiscalYear, 1, 1);
	LocalDate ends = LocalDate.of(fiscalYear, 12, 31);

	return revenueRepository.findAllByTransactionDateBetween(starts, ends).stream().map(RevenueModel::getAmount)
		.reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<ReporthRevenueMonthDto> findByMonth(Integer fiscalYear) {
	LocalDate starts = LocalDate.of(fiscalYear, 1, 1);
	LocalDate ends = LocalDate.of(fiscalYear, 12, 31);

	List<ReporthRevenueProjection> reportsProjection = revenueRepository
		.findAllByTransactionDateBetweenByMonth(starts, ends);

	List<ReporthRevenueMonthDto> reportFinal = new ArrayList<>();

	for (ReporthRevenueProjection projection : reportsProjection) {
	    BigDecimal total = projection.getTotal();
	    Integer month = Accrual.fromInteger6(projection.getMonth()).getMonth();
	    reportFinal.add(new ReporthRevenueMonthDto(String.valueOf(Month.of(month)), total));
	}

	return reportFinal;
    }
}

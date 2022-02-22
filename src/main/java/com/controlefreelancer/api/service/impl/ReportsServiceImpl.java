package com.controlefreelancer.api.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.controlefreelancer.api.dto.response.ReportByMonthResponseDto;
import com.controlefreelancer.api.dto.response.ReportTotalResponseDto;
import com.controlefreelancer.api.dto.response.ReporthRevenueMonthDto;
import com.controlefreelancer.api.service.ReportsService;
import com.controlefreelancer.api.service.RevenueService;
import com.controlefreelancer.api.service.SettingsService;

@Service
public class ReportsServiceImpl implements ReportsService {

    private final SettingsService settingsService;
    private final RevenueService revenueService;

    public ReportsServiceImpl(SettingsService settingsService, RevenueService revenueService) {
	super();
	this.settingsService = settingsService;
	this.revenueService = revenueService;
    }

    @Override
    public ReportTotalResponseDto getTotal(Integer fiscalYear) {
	BigDecimal maxRevenueAmount = settingsService.getSettings().getMaxRevenueAmount();
	BigDecimal totalRevenue = revenueService.findByFiscalYear(fiscalYear);

	return new ReportTotalResponseDto(totalRevenue, maxRevenueAmount);
    }

    @Override
    public ReportByMonthResponseDto getMonth(Integer fiscalYear) {
	BigDecimal maxRevenueAmount = settingsService.getSettings().getMaxRevenueAmount();
	List<ReporthRevenueMonthDto> revenuesMonth = revenueService.findByMonth(fiscalYear);
	return new ReportByMonthResponseDto(revenuesMonth, maxRevenueAmount);
    }

}

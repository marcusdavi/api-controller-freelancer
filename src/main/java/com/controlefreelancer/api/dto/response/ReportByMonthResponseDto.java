package com.controlefreelancer.api.dto.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReportByMonthResponseDto {

    private List<ReporthRevenueMonthDto> monthsRevenue;
    private BigDecimal maxRevenueAmount;

}

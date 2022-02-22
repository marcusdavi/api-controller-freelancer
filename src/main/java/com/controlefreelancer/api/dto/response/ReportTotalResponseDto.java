package com.controlefreelancer.api.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReportTotalResponseDto {

    private BigDecimal totalRevenue;
    private BigDecimal maxRevenueAmount;

}

package com.controlefreelancer.api.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReporthRevenueMonthDto {

    private String monthName;
    private BigDecimal monthRevenue;

}

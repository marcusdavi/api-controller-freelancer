package com.controlefreelancer.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ExpenseDto {

    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String description;

    @NotNull
    private Integer accrualDate;

    @NotNull
    private LocalDate transactionDate;

    private Integer idCustomer;

    @NotNull
    private Integer idCategory;

}

package com.controlefreelancer.api.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportTotalDto {
    
    @NotNull
    public Integer fiscalYear;

}

package com.controlefreelancer.api.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CNPJ;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerDto {

    @CNPJ
    private String cnpj;

    @NotBlank
    private String commercialName;

    @NotBlank
    private String legalName;

}

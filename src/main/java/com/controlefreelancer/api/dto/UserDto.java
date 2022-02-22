package com.controlefreelancer.api.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class UserDto {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String cnpj;

    @NotBlank
    private String companyName;

    @NotBlank
    private String phoneNumber;

}

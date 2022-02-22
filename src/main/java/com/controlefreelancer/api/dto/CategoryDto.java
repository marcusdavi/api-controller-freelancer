package com.controlefreelancer.api.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CategoryDto {
    
    @NotBlank
    private String name;

    @NotBlank
    private String description;

}

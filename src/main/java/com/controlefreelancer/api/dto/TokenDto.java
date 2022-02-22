package com.controlefreelancer.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenDto {

    private String type;
    private String value;

}

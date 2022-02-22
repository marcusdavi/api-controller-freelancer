package com.controlefreelancer.api.dto.response;

import com.controlefreelancer.api.dto.TokenDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponseDto {
    
    public TokenDto token;
    public UserResponseDto user;

}

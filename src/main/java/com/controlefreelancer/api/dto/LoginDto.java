package com.controlefreelancer.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public UsernamePasswordAuthenticationToken convert() {
	return new UsernamePasswordAuthenticationToken(email, password);
    }
}

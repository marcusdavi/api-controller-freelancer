package com.controlefreelancer.api.controller;

import javax.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controlefreelancer.api.domain.model.UserModel;
import com.controlefreelancer.api.dto.LoginDto;
import com.controlefreelancer.api.dto.TokenDto;
import com.controlefreelancer.api.dto.response.LoginResponseDto;
import com.controlefreelancer.api.dto.response.UserResponseDto;
import com.controlefreelancer.api.exception.UserNotFoundException;
import com.controlefreelancer.api.security.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authManager;

    private final TokenService tokenService;

    private final MessageSource messageSource;

    public AuthenticationController(AuthenticationManager authManager, TokenService tokenService,
	    MessageSource messageSource) {
	this.authManager = authManager;
	this.tokenService = tokenService;
	this.messageSource = messageSource;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody @Valid LoginDto form) {

	UsernamePasswordAuthenticationToken dadosLogin = form.convert();

	try {
	    Authentication authentication = authManager.authenticate(dadosLogin);
	    UserModel user = (UserModel) authentication.getPrincipal();

	    String token = tokenService.gerarToken(authentication);

	    return ResponseEntity.ok(new LoginResponseDto(new TokenDto ("Bearer", token), UserResponseDto.modelToResponse(user)));

	} catch (AuthenticationException e) {
	    throw new UserNotFoundException(
		    messageSource.getMessage("user-not-found", null, LocaleContextHolder.getLocale()));
	}

    }
}

package com.controlefreelancer.api.security;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.controlefreelancer.api.domain.model.UserModel;
import com.controlefreelancer.api.domain.repository.UserRepository;

public class AuthenticationTokenFilter extends OncePerRequestFilter{
	
	private final TokenService tokenService;
	private final UserRepository userRepository;
	
	public AuthenticationTokenFilter(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = recuperarToken(request);
		
		if(tokenService.isTokenValid(token)) {
		    autenticUser(token);
		}
		
		filterChain.doFilter(request, response);
		
	}

	private void autenticUser(String token) {
		Integer idUsuario = tokenService.getIdUser(token);
		
		UserModel usuario = userRepository.findById(idUsuario).get();
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario,  null, usuario.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	
	}
	
	

}

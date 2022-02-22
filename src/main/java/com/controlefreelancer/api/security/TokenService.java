package com.controlefreelancer.api.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.controlefreelancer.api.domain.model.UserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    @Value("${controlefreelancer.jwt.expiration}")
    private String expiration;

    @Value("${controlefreelancer.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication) {
	UserModel logged = (UserModel) authentication.getPrincipal();
	Date hoje = new Date();
	Date dateExpirated = new Date(hoje.getTime() + Long.parseLong(expiration));

	return Jwts.builder().setIssuer("API Controle Freelancer").setSubject(logged.getId().toString()).setIssuedAt(hoje)
		.setExpiration(dateExpirated).signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public boolean isTokenValid(String token) {
	try {
	    Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

    public Integer getIdUser(String token) {
	Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();

	return Integer.parseInt(claims.getSubject());
    }

}

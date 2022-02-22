package com.controlefreelancer.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.controlefreelancer.api.domain.repository.UserRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    private final AuthenticationService autenticacaoService;

    private final TokenService tokenService;

    private final UserRepository userRepository;

    public SecurityConfigurations(AuthenticationService autenticacaoService, TokenService tokenService,
	    UserRepository userRepository) {
	super();
	this.autenticacaoService = autenticacaoService;
	this.tokenService = tokenService;
	this.userRepository = userRepository;
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
	return super.authenticationManager();
    }

    // Configuraçoes de Autenticação
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());

    }

    // Configuraçoes de Autorização
    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests().antMatchers(HttpMethod.POST, "/auth").permitAll().antMatchers("/h2-console/**")
		.permitAll().anyRequest().authenticated().and().csrf().disable().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().headers().frameOptions().sameOrigin()
		.and().addFilterBefore(new AuthenticationTokenFilter(tokenService, userRepository),
			UsernamePasswordAuthenticationFilter.class);
    }

    // Configurações de recursos estáticos (js css, imagens, etc.)
    @Override
    public void configure(WebSecurity web) throws Exception {

	web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**",
		"/swagger-resources/**");
    }
}

package com.controlefreelancer.api.security;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.controlefreelancer.api.domain.model.UserModel;
import com.controlefreelancer.api.domain.repository.UserRepository;
import com.controlefreelancer.api.exception.UserNotFoundException;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepository repository;
    private final MessageSource messageSource;

    public AuthenticationService(UserRepository repository, MessageSource messageSource) {
	this.repository = repository;
	this.messageSource = messageSource;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Optional<UserModel> usuario = repository.findByEmail(username);

	if (usuario.isPresent()) {
	    return usuario.get();
	}

	throw new UserNotFoundException(
		messageSource.getMessage("user-not-found", null, LocaleContextHolder.getLocale()));
    }

}

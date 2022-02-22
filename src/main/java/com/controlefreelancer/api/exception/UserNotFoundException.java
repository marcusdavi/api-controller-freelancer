package com.controlefreelancer.api.exception;

public class UserNotFoundException extends NegocioException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String mensagem) {
	super(mensagem);
    }

}

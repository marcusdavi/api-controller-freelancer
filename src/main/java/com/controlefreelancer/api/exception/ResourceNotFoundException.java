package com.controlefreelancer.api.exception;

public class ResourceNotFoundException extends NegocioException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String mensagem) {
	super(mensagem);
    }

}

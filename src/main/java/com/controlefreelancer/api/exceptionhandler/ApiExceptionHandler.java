package com.controlefreelancer.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.controlefreelancer.api.exception.NegocioException;
import com.controlefreelancer.api.exception.ResourceNotFoundException;
import com.controlefreelancer.api.exception.UserNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    final MessageSource messageSource;

    public ApiExceptionHandler(MessageSource messageSource) {
	this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	    HttpHeaders headers, HttpStatus status, WebRequest request) {

	List<Field> fields = new ArrayList<>();

	for (ObjectError error : ex.getBindingResult().getAllErrors()) {
	    String name = ((FieldError) error).getField();
	    String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
	    fields.add(new Field(name, message));
	}

	Problem problema = buildProblem("Invalid(s) field(s)", status);
	problema.setFields(fields);

	return handleExceptionInternal(ex, problema, headers, status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request) {

	return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }
    
    

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

	return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);

    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {

	return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);

    }

    private Problem buildProblem(String message, HttpStatus status) {
	Problem problem = new Problem();
	problem.setStatus(status.value());
	problem.setDateTime(OffsetDateTime.now());
	problem.setTitle(message);
	return problem;
    }

}

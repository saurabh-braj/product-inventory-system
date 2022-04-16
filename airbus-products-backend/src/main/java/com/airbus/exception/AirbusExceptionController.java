package com.airbus.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AirbusExceptionController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ProductNotFoundException.class)
	ResponseEntity<ExceptionResponce> productNotFoundHandler(ProductNotFoundException execption) {
		ExceptionResponce exceptionResponce = new ExceptionResponce();
		exceptionResponce.setStatusCode(HttpStatus.NOT_FOUND.value());
		exceptionResponce.setMessage(execption.getMessage());
		exceptionResponce.setDetails(execption.toString());
		return buildErrorResponse(exceptionResponce, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(TokenAuthenticationException.class)
	ResponseEntity<ExceptionResponce> jwtTokenExceptionHandler(TokenAuthenticationException execption) {
		ExceptionResponce exceptionResponce = new ExceptionResponce();
		exceptionResponce.setStatusCode(HttpStatus.FORBIDDEN.value());
		exceptionResponce.setMessage(execption.getMessage());
		exceptionResponce.setDetails(execption.toString());
		return buildErrorResponse(exceptionResponce, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	ResponseEntity<ExceptionResponce> genericExceptionHandler(Exception execption) {
		ExceptionResponce exceptionResponce = new ExceptionResponce();
		exceptionResponce.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		exceptionResponce.setMessage(execption.getMessage());
		exceptionResponce.setDetails(execption.toString());
		return buildErrorResponse(exceptionResponce, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	ResponseEntity<ExceptionResponce> userNotFoundExceptionHandler(UserNotFoundException execption) {
		ExceptionResponce exceptionResponce = new ExceptionResponce();
		exceptionResponce.setStatusCode(HttpStatus.NOT_FOUND.value());
		exceptionResponce.setMessage(execption.getMessage());
		exceptionResponce.setDetails(execption.toString());
		return buildErrorResponse(exceptionResponce, HttpStatus.NOT_FOUND);
	}

	private ResponseEntity<ExceptionResponce> buildErrorResponse(ExceptionResponce exceptionResponce,
			HttpStatus status) {
		return new ResponseEntity<>(exceptionResponce, status);
	}

}

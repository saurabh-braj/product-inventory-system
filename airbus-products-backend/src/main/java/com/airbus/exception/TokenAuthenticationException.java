package com.airbus.exception;

public class TokenAuthenticationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public TokenAuthenticationException(String message) {
		super(message);
	}

}

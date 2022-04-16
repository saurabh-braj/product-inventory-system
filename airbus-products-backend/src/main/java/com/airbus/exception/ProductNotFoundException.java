package com.airbus.exception;

public class ProductNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(Long id) {
		super("Product is not found for Id: " + id);
	}

}

package com.airbus.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponce {
	
	private Integer statusCode;
	private String message;
	private String details;
	
}

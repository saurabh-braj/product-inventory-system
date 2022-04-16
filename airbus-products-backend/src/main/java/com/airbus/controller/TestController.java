package com.airbus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airbus.APIConstants;

@RestController
@RequestMapping(APIConstants.TEST)
public class TestController {
	
	@GetMapping
	public ResponseEntity<String> hello() {
		return new ResponseEntity<>("Application is Working !", HttpStatus.OK);
	}

}

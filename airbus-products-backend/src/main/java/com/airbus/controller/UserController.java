package com.airbus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airbus.APIConstants;
import com.airbus.domain.JwtToken;
import com.airbus.entity.User;
import com.airbus.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping(APIConstants.USERS)
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("refresh-token")
	public ResponseEntity<JwtToken> refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ResponseEntity<JwtToken>(userService.refreshToken(request), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<?> createNewUser(@RequestBody User user) {
		userService.createUser(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}


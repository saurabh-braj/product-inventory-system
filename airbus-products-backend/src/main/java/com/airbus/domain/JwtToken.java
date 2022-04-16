package com.airbus.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class JwtToken {
	
	private String accessToken;
	private String refreshToken;
	private String username;

}

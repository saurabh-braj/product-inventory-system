package com.airbus.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.CollectionUtils;

import com.airbus.domain.JwtToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JWTUtility {

	private static String secretKey = "airbus-security-key";

	private static String issuer = "http://airbus.inventory.com";

	private static Integer accessTokenExpiry = 30;

	private static Integer refreshTokenExpiry = 60;

	public static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

	public static final String CLAIM_NAME = "roles";

	public static JwtToken createJwtToken(User user) {
		JwtToken jwtToken = new JwtToken();
		jwtToken.setAccessToken(
				JWTUtility.generateAccessToken(user.getUsername(), setAuthorities(user.getAuthorities())));
		jwtToken.setRefreshToken(JWTUtility.generateRefreshToken(user.getUsername()));
		jwtToken.setUsername(user.getUsername());
		return jwtToken;
	}

	public static String generateAccessToken(String userName, List<String> roles) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
		String accessToken = JWT.create().withSubject(userName)
				.withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpiry * 60 * 1000)).withIssuer(issuer)
				.withClaim(CLAIM_NAME, roles).sign(algorithm);
		return accessToken;
	}

	public static String generateRefreshToken(String userName) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
		String refreshToken = JWT.create().withSubject(userName)
				.withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpiry * 60 * 1000)).withIssuer(issuer)
				.sign(algorithm);
		return refreshToken;
	}

	public static String getJwtTokenFromHeader(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
			return authorizationHeader.substring(AUTHORIZATION_HEADER_PREFIX.length());
		}
		return null;
	}

	public static DecodedJWT decodeJwtToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
		JWTVerifier verifier = JWT.require(algorithm).build();
		return verifier.verify(token);
	}

	public static String getUserNameFromToken(DecodedJWT decodedJWT) {
		return decodedJWT.getSubject();
	}

	public static List<String> getClaimsFromToken(DecodedJWT decodedJWT) {
		return decodedJWT.getClaim(CLAIM_NAME).asList(String.class);
	}

	public static Collection<SimpleGrantedAuthority> getUserAuthorities(List<String> roles) {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if (!CollectionUtils.isEmpty(roles)) {
			roles.forEach(role -> {
				authorities.add(new SimpleGrantedAuthority(role));
			});
		}
		return authorities;
	}
	
	private static List<String> setAuthorities(Collection<GrantedAuthority> grantedAuthority) {
		return grantedAuthority.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
	}

}

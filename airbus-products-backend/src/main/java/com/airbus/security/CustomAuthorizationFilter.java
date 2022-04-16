package com.airbus.security;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.airbus.APIConstants;
import com.airbus.exception.TokenAuthenticationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (APIConstants.whiteListUrls.contains(request.getServletPath())
				|| HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
			filterChain.doFilter(request, response);
		} else {
			String jwtToken = JWTUtility.getJwtTokenFromHeader(request);
			if (Objects.nonNull(jwtToken)) {
				try {
					SecurityContextHolder.getContext().setAuthentication(setAuthenticationToken(jwtToken));
				} catch (Exception e) {
					throw new TokenAuthenticationException(e.getMessage());
				}
				filterChain.doFilter(request, response);
			} else {
				throw new TokenAuthenticationException("Valid JWT Token is required to access this resource.");
			}
		}
	}

	private UsernamePasswordAuthenticationToken setAuthenticationToken(String jwtToken) {
		DecodedJWT decodedJwt = JWTUtility.decodeJwtToken(jwtToken);
		String userName = JWTUtility.getUserNameFromToken(decodedJwt);
		List<String> roles = JWTUtility.getClaimsFromToken(decodedJwt);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userName, null, JWTUtility.getUserAuthorities(roles));
		return usernamePasswordAuthenticationToken;
	}

}

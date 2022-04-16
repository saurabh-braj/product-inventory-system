package com.airbus.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.airbus.domain.JwtToken;
import com.airbus.entity.Role;
import com.airbus.entity.User;
import com.airbus.exception.UserNotFoundException;
import com.airbus.repository.UserRepository;
import com.airbus.security.JWTUtility;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		this.userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
		User user = userRepository.findByUserName(username)
				.orElseThrow(() -> new UserNotFoundException("User not found - " + username));
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				setUserAuthorities(user));
	}

	private Collection<SimpleGrantedAuthority> setUserAuthorities(User user) {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return authorities;
	}

	public JwtToken refreshToken(HttpServletRequest request) {
		String refresh_token = JWTUtility.getJwtTokenFromHeader(request);
		DecodedJWT decodedJwt = JWTUtility.decodeJwtToken(refresh_token);
		String username = JWTUtility.getUserNameFromToken(decodedJwt);
		User user = userRepository.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found - " + username));

		JwtToken jwtToken = new JwtToken();
		jwtToken.setAccessToken(JWTUtility.generateAccessToken(user.getUserName(), getUserRoles(user.getRoles())));
		jwtToken.setRefreshToken(JWTUtility.generateRefreshToken(user.getUserName()));
		return jwtToken;
	}

	private List<String> getUserRoles(List<Role> roles) {
		return roles.stream().map(Role::getName).collect(Collectors.toList());
	}

}

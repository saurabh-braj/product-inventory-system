//package com.airbus.controller;
//
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultMatcher;
//
//import com.airbus.APIConstants;
//import com.airbus.entity.User;
//import com.airbus.security.JWTUtility;
//import com.airbus.service.UserService;
//
//@SpringBootTest()
//@AutoConfigureMockMvc
//public class ProductControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@Autowired
//	private UserService userService;
//
//	@BeforeEach
//	public void init() {
//		User user = new User();
//		user.setUserName("saurabh.braj@gmail.com");
//		user.setPassword("Password@123");
//		List<String> roles = new ArrayList<>();
//		roles.add("Admin");
//		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUserName(),
//				null, JWTUtility.getUserAuthorities(roles)));
//
//		Mockito.when(userService.loadUserByUsername(user.getUserName())).thenReturn((UserDetails) user);
//
//	}
//
//	@Test
//	public void getAllProducts() throws Exception {
//		mockMvc.perform(get(APIConstants.PRODUCTS).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
//				.andExpect((ResultMatcher) content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
//	}
//
//}

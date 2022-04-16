package com.airbus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class APIConstants {
	
	public static final String SECURED = "/secured/api";
	
	public static final String UNSECURED = "/unsecured/api";

	public static final String PRODUCTS = SECURED + "/products";
	
	public static final String REFERENCE = SECURED + "/reference";
	
	public static final String TEST = UNSECURED + "/test";
	
	public static final String USERS = UNSECURED + "/users";
	
	public static final List<String> whiteListUrls = 
		    Collections.unmodifiableList(Arrays.asList("/login", TEST, USERS, USERS+"/refresh-token"));

}

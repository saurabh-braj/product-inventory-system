package com.airbus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airbus.APIConstants;
import com.airbus.entity.ProductCategory;
import com.airbus.service.ReferenceDataService;

@RestController
@RequestMapping(APIConstants.REFERENCE)
public class ReferenceDataController {

	private final ReferenceDataService referenceDataService;

	public ReferenceDataController(ReferenceDataService referenceDataService) {
		this.referenceDataService = referenceDataService;
	}

	@GetMapping("product-categories")
	public ResponseEntity<List<ProductCategory>> getAllProductCategory() {
		return new ResponseEntity<>(referenceDataService.getAllProductCategory(), HttpStatus.OK);
	}
}

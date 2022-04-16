package com.airbus.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.airbus.entity.ProductCategory;
import com.airbus.repository.ProductCategoryRepository;

@Service
public class ReferenceDataService {

	private final ProductCategoryRepository productCategoryRepository;
	
	public ReferenceDataService(ProductCategoryRepository productCategoryRepository) {
		this.productCategoryRepository = productCategoryRepository;
	}
	
	public List<ProductCategory> getAllProductCategory() {
		return productCategoryRepository.findAll();
	}
}

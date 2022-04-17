package com.airbus.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.airbus.entity.Product;
import com.airbus.entity.ProductCategory;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

	List<Product> findByCategory(ProductCategory category);
	
	Page<Product> findByProductIdContainingIgnoreCaseAndNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(
			String productId, String name, String description, Pageable pageable);
	
	Page<Product> findByProductIdContainingIgnoreCaseAndCategoryAndNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(
			String productId, ProductCategory category, String name, String description, Pageable pageable);
}

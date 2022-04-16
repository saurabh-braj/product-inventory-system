package com.airbus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airbus.entity.Product;
import com.airbus.entity.ProductCategory;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByCategory(ProductCategory category);

}

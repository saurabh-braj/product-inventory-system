package com.airbus.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.airbus.entity.Product;
import com.airbus.entity.ProductCategory;
import com.airbus.exception.ProductNotFoundException;
import com.airbus.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public List<Product> getAllProducts() {
		return this.productRepository.findAll();
	}
	
	public Product getProductById(Long id) {
		return this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
	}
	
	public Product saveProduct(Product product) {
		return this.productRepository.save(product);
	}
	
	public Product updateProduct(Product newProduct, Long id) {
		return productRepository.findById(id).map(product -> {
			product.setProductId(newProduct.getProductId());
			product.setCategory(newProduct.getCategory());
			product.setName(newProduct.getName());
			product.setDescription(newProduct.getDescription());
			product.setUnits(newProduct.getUnits());
			return productRepository.save(product);
		}).orElseGet(() -> {
			newProduct.setId(id);
			return productRepository.save(newProduct);
		});
	}
	
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
	
	public List<Product> getProductsByCategory(Long categoryId) {
		ProductCategory category = new ProductCategory();
		category.setId(categoryId);
		return this.productRepository.findByCategory(category);
	}
	
	public List<Product> getFilteredData(Product product) {
		List<Product> products = this.productRepository.findAll();
		if (Objects.nonNull(product)) {
			if (product.getProductId() != null && !"".equals(product.getProductId().trim())) {
				products = products.stream().filter(item -> item.getProductId().toLowerCase().contains(product.getProductId().toLowerCase()))
						.collect(Collectors.toList());
			}
			if (product.getName() != null && !"".equals(product.getName().trim())) {
				products = products.stream().filter(item -> item.getName().toLowerCase().contains(product.getName().toLowerCase()))
						.collect(Collectors.toList());
			}
			if (product.getDescription() != null && !"".equals(product.getDescription().trim())) {
				products = products.stream().filter(item -> item.getDescription().toLowerCase().contains(product.getDescription().toLowerCase()))
						.collect(Collectors.toList());
			}
			if (product.getCategory() != null && product.getCategory().getId() != null) {
				products = products.stream().filter(item -> item.getCategory().getId().equals(product.getCategory().getId()))
						.collect(Collectors.toList());
			}
		}
		return products;
	}
}

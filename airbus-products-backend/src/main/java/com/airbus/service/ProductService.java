package com.airbus.service;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.airbus.domain.PageRequestModel;
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

	public Page<Product> getAllProducts(PageRequestModel pageRequestModel) throws Exception {
		Pageable pageable = PageRequest.of(pageRequestModel.getPageNumber(), pageRequestModel.getPageSize(),
				Sort.by(pageRequestModel.getSortDirection(), pageRequestModel.getSortColumn()));

		if (Objects.nonNull(pageRequestModel) && Objects.nonNull(pageRequestModel.getProduct())
				&& Objects.nonNull(pageRequestModel.getProduct().getCategory())
				&& Objects.nonNull(pageRequestModel.getProduct().getCategory().getId())) {
			return this.productRepository.findByProductIdContainingIgnoreCaseAndCategoryAndNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(
					pageRequestModel.getProduct().getProductId(), pageRequestModel.getProduct().getCategory(),
					pageRequestModel.getProduct().getName(), pageRequestModel.getProduct().getDescription(), pageable);
		} else {
			return this.productRepository.findByProductIdContainingIgnoreCaseAndNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(
					pageRequestModel.getProduct().getProductId(), pageRequestModel.getProduct().getName(),
					pageRequestModel.getProduct().getDescription(), pageable);
		}
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
}

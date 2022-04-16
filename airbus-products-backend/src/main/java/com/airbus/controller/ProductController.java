package com.airbus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airbus.APIConstants;
import com.airbus.entity.Product;
import com.airbus.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(APIConstants.PRODUCTS)
@Slf4j
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {
		log.info("Get All Products List !");
		return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		log.info("Get Product Details By Id : {}", id);
		return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		log.info("Add A New Product!");
		return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long id) {
		log.info("Update Product Details for Id : {}", id);
		return new ResponseEntity<>(productService.updateProduct(product, id), HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		log.info("Delete Product By Id : {}", id);
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("category/{categoryId}")
	public ResponseEntity<List<Product>> getAllProductsByCategory(@PathVariable Long categoryId) {
		log.info("Get All Products for Category - {}", categoryId);
		return new ResponseEntity<>(productService.getProductsByCategory(categoryId), HttpStatus.OK);
	}
	
	@PostMapping("filter")
	public ResponseEntity<List<Product>> getAllProductsByName(@RequestBody Product product) {
		return new ResponseEntity<>(productService.getFilteredData(product), HttpStatus.OK);
	}

}

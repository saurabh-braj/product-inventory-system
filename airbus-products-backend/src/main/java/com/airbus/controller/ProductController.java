package com.airbus.controller;

import java.util.List;

import org.springframework.data.domain.Page;
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
import com.airbus.domain.PageRequestModel;
import com.airbus.entity.Product;
import com.airbus.service.ProductService;

@RestController
@RequestMapping(APIConstants.PRODUCTS)
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping("paginated")
	public ResponseEntity<Page<Product>> getAllProducts(@RequestBody PageRequestModel pageRequestModel) throws Exception {
		return new ResponseEntity<>(productService.getAllProducts(pageRequestModel), HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long id) {
		return new ResponseEntity<>(productService.updateProduct(product, id), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("category/{categoryId}")
	public ResponseEntity<List<Product>> getAllProductsByCategory(@PathVariable Long categoryId) {
		return new ResponseEntity<>(productService.getProductsByCategory(categoryId), HttpStatus.OK);
	}
}

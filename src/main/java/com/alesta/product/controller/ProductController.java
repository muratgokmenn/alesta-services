package com.alesta.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alesta.product.model.Product;
import com.alesta.product.service.ProductService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
//@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	//@PostMapping("/register")
	@PostMapping(value = "/api/products/register", consumes = "application/json", produces = "application/json")
	public Product saveProduct(@RequestBody Product product) {
		return productService.saveProduct(product);
	}
	
	@GetMapping("/{productId}")
	public Product getProductsById(@PathVariable Long productId) throws Exception {
		return productService.getProductsByProductId(productId).orElseThrow( () -> new Exception("Product did not found"));
	}

}

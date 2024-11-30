package com.alesta.product.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alesta.product.model.Product;
import com.alesta.product.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;

	public Optional<Product> getProductsByProductId(Long productId) {
		return productRepository.findById(productId);
	}

	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

}

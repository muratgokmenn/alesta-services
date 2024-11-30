package com.alesta.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alesta.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}

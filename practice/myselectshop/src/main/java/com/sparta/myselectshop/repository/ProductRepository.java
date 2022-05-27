package com.sparta.myselectshop.repository;

import com.sparta.myselectshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
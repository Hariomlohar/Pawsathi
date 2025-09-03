package com.PawSathi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.PawSathi.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> { 
    List<Product> findByCategoryId(int categoryId);
    List<Product> findByNameContainingIgnoreCase(String name);

}

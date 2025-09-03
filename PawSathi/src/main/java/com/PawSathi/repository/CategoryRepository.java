package com.PawSathi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PawSathi.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public Boolean existsByName(String name);
}

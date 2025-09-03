package com.PawSathi.services;

import java.util.List;

import com.PawSathi.model.Category;

public interface CategoryService {

    public Category saveCategory(Category category);

    public Boolean existCategory(String name);

    public List<Category> getAllCategory();

    public Category getCategoryById(int id);  // NEW

    public void deleteCategory(int id);       // NEW
}

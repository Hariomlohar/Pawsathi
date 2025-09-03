package com.PawSathi.service.imple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PawSathi.model.Category;
import com.PawSathi.model.Product;
import com.PawSathi.repository.CategoryRepository;
import com.PawSathi.services.CategoryService;

@Service
public class CategoryServiceImple implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    

    @Override
    public Category saveCategory(Category category) {
       return categoryRepository.save(category);
    }

    @Override
    public Boolean existCategory(String name) {
       return categoryRepository.existsByName(name);
    }

    @Override
    public List<Category> getAllCategory() {
       return categoryRepository.findAll();
    }

     @Override
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
    
}

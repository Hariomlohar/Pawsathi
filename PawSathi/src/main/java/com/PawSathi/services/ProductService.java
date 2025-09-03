package com.PawSathi.services;

import com.PawSathi.model.Product;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    Product saveProduct(Product product);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(int categoryId);
    void deleteProduct(int id);
    Product getProductById(int id);
    void updateProduct(Product product, MultipartFile file, String uploadDir);
    List<Product> searchProductsByName(String name);

}

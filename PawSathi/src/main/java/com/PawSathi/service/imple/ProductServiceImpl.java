package com.PawSathi.service.imple;

import com.PawSathi.model.Product;
import com.PawSathi.repository.CategoryRepository;
import com.PawSathi.repository.ProductRepository;
import com.PawSathi.services.FileService;
import com.PawSathi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileService fileService;    

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

     @Override
    public List<Product> getProductsByCategory(int categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }


    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(int id) {
    return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
}

@Override
public void updateProduct(Product product, MultipartFile file, String uploadDir) {
    Product existingProduct = getProductById(product.getId());

    if (!file.isEmpty()) {
        // Delete old image
        String oldFilePath = uploadDir + existingProduct.getImageName();
        fileService.deleteFile(oldFilePath);

        // Upload new image
        String newFileName = fileService.storeFile(file, uploadDir);
        product.setImageName(newFileName);
    } else {
        // Keep old image
        product.setImageName(existingProduct.getImageName());
    }

    // Save updated product
    productRepository.save(product);
}

@Override
public List<Product> searchProductsByName(String name) {
    return productRepository.findByNameContainingIgnoreCase(name);
}


}

package com.PawSathi.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.PawSathi.model.Product;
import com.PawSathi.services.CategoryService;
import com.PawSathi.services.ProductService;

@Controller
public class ProductController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/view_product/{id}")
    public String viewProductDetails(@PathVariable("id") Integer id, Model model) {
        // Fetch product by ID
        Product product = productService.getProductById(id);
        if (product == null) {
            // Handle not found (redirect or show error page)
            return "redirect:/product";
        }
        model.addAttribute("product", product);
        return "view_product"; // Your product details page
    }

}

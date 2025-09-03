package com.PawSathi.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.PawSathi.model.Product;
import com.PawSathi.services.CategoryService;
import com.PawSathi.services.ProductService;

@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/add_to_cart")
    public String addToCart(){
        return "add_to_Card";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/view_product")
    public String view_product() {
        return "view_product";
    }

    @GetMapping("/")
    public String homePage(Model model) {
        // Reuse the same service method as the category page
        model.addAttribute("categorys", categoryService.getAllCategory());
        List<Product> products = productService.getAllProducts(); // or getHotDeals()
        model.addAttribute("products", products);
        return "home"; // your home.html
    }

    // Show all products with category & search filter
    @GetMapping("/products")
    public String viewProducts(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "search", required = false) String search,
            Model model) {

        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("selectedCategoryId", categoryId);
        

        if (search != null && !search.trim().isEmpty()) {
            // Search overrides category filter if provided
            model.addAttribute("products", productService.searchProductsByName(search));
            model.addAttribute("search", search);
        } else if (categoryId != null) {
            model.addAttribute("products", productService.getProductsByCategory(categoryId));
        } else {
            model.addAttribute("products", productService.getAllProducts());
        }
      
        return "products"; // Your HTML page name
    }



}

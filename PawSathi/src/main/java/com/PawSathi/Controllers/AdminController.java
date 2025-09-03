package com.PawSathi.Controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.PawSathi.model.Category;
import com.PawSathi.model.Product;
import com.PawSathi.services.CategoryService;
import com.PawSathi.services.FileService;
import com.PawSathi.services.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @GetMapping("/home")
    public String admin_home() {
        return "admin/admin_home";
    }

    @GetMapping("/add_product")
    public String add_product(Model model) {
        model.addAttribute("categorys", categoryService.getAllCategory());
        return "admin/add_product";
    }

    @GetMapping("/add_category")
    public String add_category(Model m) {
        m.addAttribute("categorys", categoryService.getAllCategory());
        return "admin/add_category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category,
            HttpSession httpSession,
            @RequestParam("file") MultipartFile file) throws IOException {

        String imageName = file != null && !file.isEmpty() ? file.getOriginalFilename() : "default.jpg";
        category.setImageName(imageName);

        if (categoryService.existCategory(category.getName())) {
            httpSession.setAttribute("errorMsg", "Category Name already exists");
        } else {
            Category saveCategory = categoryService.saveCategory(category);
            if (ObjectUtils.isEmpty(saveCategory)) {
                httpSession.setAttribute("errorMsg", "Not saved! Internal server error");
            } else {
                // Create upload directory if it doesn't exist
                String uploadDir = System.getProperty("user.dir") + "/uploads/category_img/";
                File directory = new File(uploadDir);
                if (!directory.exists())
                    directory.mkdirs();

                // Save file to uploads/category_img folder
                Path path = Paths.get(uploadDir + imageName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                httpSession.setAttribute("succMsg", "Successfully saved!");
            }
        }
        return "redirect:/admin/add_category";
    }

    // for edite and delete the category

    @GetMapping("/edit_category")
    public String editCategory(@RequestParam("id") int id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        model.addAttribute("categorys", categoryService.getAllCategory());
        return "admin/add_category"; // Reuse same page for editing
    }

    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute Category category,
            @RequestParam("file") MultipartFile file,
            HttpSession httpSession) throws IOException {
        Category existing = categoryService.getCategoryById(category.getId());
        if (existing == null) {
            httpSession.setAttribute("errorMsg", "Category not found");
            return "redirect:/admin/add_category";
        }

        // Handle image update
        String imageName = file != null && !file.isEmpty() ? file.getOriginalFilename() : existing.getImageName();
        category.setImageName(imageName);

        // Save updated category
        Category updated = categoryService.saveCategory(category);

        // If new image uploaded, replace it
        if (file != null && !file.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/uploads/category_img/";
            File directory = new File(uploadDir);
            if (!directory.exists())
                directory.mkdirs();

            Path path = Paths.get(uploadDir + imageName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }

        httpSession.setAttribute("succMsg", "Category updated successfully!");
        return "redirect:/admin/add_category";
    }

    @GetMapping("/delete_category")
    public String deleteCategory(@RequestParam("id") int id, HttpSession httpSession) {
        categoryService.deleteCategory(id);
        httpSession.setAttribute("succMsg", "Category deleted successfully!");
        return "redirect:/admin/add_category";
    }

    // ..............................Save Product
    // details...............................

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute Product product,
            @RequestParam("file") MultipartFile file,
            @RequestParam("categoryId") int categoryId,
            HttpSession session) throws IOException {

        // Set Category
        Category category = categoryService.getCategoryById(categoryId);
        product.setCategory(category);

        // Handle Image Upload
        String imageName = file != null && !file.isEmpty() ? file.getOriginalFilename() : "default.jpg";
        product.setImageName(imageName);

        String uploadDir = System.getProperty("user.dir") + "/uploads/product_img/";
        File directory = new File(uploadDir);
        if (!directory.exists())
            directory.mkdirs();

        Path path = Paths.get(uploadDir + imageName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        // Save Product
        productService.saveProduct(product);
        session.setAttribute("succMsg", "Product added successfully!");
        return "redirect:/admin/add_product";
    }

    // ...............................List of product......................

    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/product_list"; // Your HTML page
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable int id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("succMsg", "Product deleted successfully!");
        return "redirect:/admin/products";
    }

    // Show Edit Product Form
    @GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categories", categoryService.getAllCategory());
        return "admin/edit_product";
    }

    // Update Product
    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        String uploadDir = System.getProperty("user.dir") + "/uploads/product_img/";
        productService.updateProduct(product, file, uploadDir);
        redirectAttributes.addFlashAttribute("succMsg", "Product updated successfully!");
        return "redirect:/admin/products";
    }

}

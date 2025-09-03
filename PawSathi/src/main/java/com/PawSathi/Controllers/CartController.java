package com.PawSathi.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.PawSathi.model.CartItem;
import com.PawSathi.model.Product;
import com.PawSathi.service.imple.CartService;
import com.PawSathi.services.ProductService;



@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Product product = productService.getProductById(id);
        cartService.addToCart(product);
        redirectAttributes.addFlashAttribute("succMsg", "Product added to cart!");
        return "redirect:/products";
    }

    @GetMapping("/add_cart")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("total", cartService.getTotalPrice());
        return "add_to_Card";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable int id) {
        cartService.removeItem(id);
        return "redirect:/cart/add_cart";
    }

    @GetMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart/add_cart";
    }

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model) {
        // Get cart data
        List<CartItem> cartItems = cartService.getCartItems();
        double subtotal = cartService.getTotalPrice();
        double shipping = 50.0; // fixed for now
        double total = subtotal + shipping;

        // Add data to model
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("shipping", shipping);
        model.addAttribute("total", total);

        return "checkout";
    }

    @PostMapping("/placeOrder")
    public String placeOrder(RedirectAttributes redirectAttributes) {
        // TODO: Save order in DB
        cartService.clearCart(); // Empty cart after placing order
        redirectAttributes.addFlashAttribute("succMsg", "Your order has been placed successfully!");
        return "redirect:/products";
    }
}

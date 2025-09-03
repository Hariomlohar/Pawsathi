package com.PawSathi.service.imple;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.PawSathi.model.CartItem;
import com.PawSathi.model.Product;

@Service
@SessionScope
public class CartService {
    
    private List<CartItem> cartItems = new ArrayList<>();

    public void addToCart(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        cartItems.add(new CartItem(product, 1));
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public double getTotalPrice() {
        return cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    public void clearCart() {
        cartItems.clear();
    }

    public void removeItem(int productId) {
        cartItems.removeIf(item -> item.getProduct().getId() == productId);
    }
    
}


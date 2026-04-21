package com.johnteacher.shoppingcart.service.cart;

import com.johnteacher.shoppingcart.model.Cart;
import com.johnteacher.shoppingcart.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    public Cart initializeNewCart(User user);
    Cart getCartByUserId(Long userId);
}

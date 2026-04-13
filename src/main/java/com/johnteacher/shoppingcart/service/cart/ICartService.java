package com.johnteacher.shoppingcart.service.cart;

import com.johnteacher.shoppingcart.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    public Long initializeNewCart();
    Cart getCartByUserId(Long userId);
}

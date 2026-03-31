package com.johnteacher.shoppingcart.service.cart;

import com.johnteacher.shoppingcart.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeCartItem(Long cartId, Long productId);
    void updateCartItem(Long cartId, Long productId, int quantity);
}

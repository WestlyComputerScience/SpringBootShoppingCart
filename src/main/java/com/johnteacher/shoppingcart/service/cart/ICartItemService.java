package com.johnteacher.shoppingcart.service.cart;

import com.johnteacher.shoppingcart.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeCartItem(Long cartId, Long productId);
    public void updateItemQuantity(Long cartId, Long productId, int quantity);
    public CartItem getCartItem(Long cartId, Long productId);
}

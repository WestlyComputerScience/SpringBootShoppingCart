package com.johnteacher.shoppingcart.service.cart;

import com.johnteacher.shoppingcart.repository.CartItemRepository;
import com.johnteacher.shoppingcart.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{
    private final CartItemRepository cartItemRepository;
    private final IProductService productService;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {

    }

    @Override
    public void removeCartItem(Long cartId, Long productId) {

    }

    @Override
    public void updateCartItem(Long cartId, Long productId, int quantity) {

    }
}

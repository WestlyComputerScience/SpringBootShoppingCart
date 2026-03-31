package com.johnteacher.shoppingcart.service.cart;

import com.johnteacher.shoppingcart.exceptions.ResourceNotFoundException;
import com.johnteacher.shoppingcart.model.Cart;
import com.johnteacher.shoppingcart.repository.CartItemRepository;
import com.johnteacher.shoppingcart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service // also makes this class a spring bean
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount); // sets the cart to the new total amount
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        // clear all items in the cart
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        // delete cart by id
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
//        return cart.getCartItems().stream()
//                .map(CartItem::getTotalPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add); // accumulate all total cart prices
        return cart.getTotalAmount(); // same as above
    }
}

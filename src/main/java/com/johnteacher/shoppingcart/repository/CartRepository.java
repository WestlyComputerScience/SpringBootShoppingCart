package com.johnteacher.shoppingcart.repository;

import com.johnteacher.shoppingcart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}

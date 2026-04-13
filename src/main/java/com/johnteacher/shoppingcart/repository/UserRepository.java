package com.johnteacher.shoppingcart.repository;

import com.johnteacher.shoppingcart.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByEmail(String email);
}

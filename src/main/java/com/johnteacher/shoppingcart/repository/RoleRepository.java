package com.johnteacher.shoppingcart.repository;

import com.johnteacher.shoppingcart.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String role);
}

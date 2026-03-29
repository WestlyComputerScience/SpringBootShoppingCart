package com.johnteacher.shoppingcart.repository;

import com.johnteacher.shoppingcart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}

package com.johnteacher.shoppingcart.request;

import com.johnteacher.shoppingcart.model.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

// find to add @Data since it's not an entity
@Data // allows getter and setter etc.
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}

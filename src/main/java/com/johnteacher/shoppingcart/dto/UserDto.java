package com.johnteacher.shoppingcart.dto;

import com.johnteacher.shoppingcart.model.Cart;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
}

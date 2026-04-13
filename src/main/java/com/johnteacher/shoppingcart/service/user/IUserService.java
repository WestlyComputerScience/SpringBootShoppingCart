package com.johnteacher.shoppingcart.service.user;

import com.johnteacher.shoppingcart.dto.UserDto;
import com.johnteacher.shoppingcart.model.User;
import com.johnteacher.shoppingcart.request.CreateUserRequest;
import com.johnteacher.shoppingcart.request.UserUpdateRequest;

public interface IUserService {
    User getUserByUserId(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);
    UserDto convertToDto(User user);
}

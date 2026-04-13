package com.johnteacher.shoppingcart.service.user;

import com.johnteacher.shoppingcart.dto.UserDto;
import com.johnteacher.shoppingcart.exceptions.AlreadyExistsException;
import com.johnteacher.shoppingcart.exceptions.ResourceNotFoundException;
import com.johnteacher.shoppingcart.model.User;
import com.johnteacher.shoppingcart.repository.UserRepository;
import com.johnteacher.shoppingcart.request.CreateUserRequest;
import com.johnteacher.shoppingcart.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User id:" + userId +  " was not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException("Oops! " + request.getEmail() + " already exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User id:" + userId + " was not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete,
                        () -> {throw new ResourceNotFoundException("User id:" + userId +  " was not found");
                });
    }

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}

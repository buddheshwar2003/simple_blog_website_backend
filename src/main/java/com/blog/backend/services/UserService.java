package com.blog.backend.services;

import com.blog.backend.domain.dtos.response.UserResponseDto;
import com.blog.backend.domain.entities.User;
import com.blog.backend.mapper.UserMapper;
import com.blog.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDto getUserData(UUID id, String username) {
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        User currentUser = userRepository.findByEmail(username).orElseThrow(()->new RuntimeException("User not valid"));
        return userMapper.toResponse(user);
    }
}

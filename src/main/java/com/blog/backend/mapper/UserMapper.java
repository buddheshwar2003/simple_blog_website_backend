package com.blog.backend.mapper;

import com.blog.backend.domain.dtos.response.UserResponseDto;
import com.blog.backend.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDto toResponse(User user1){
        UserResponseDto user = new UserResponseDto();
        user.setId(user1.getId());
        user.setEmail(user1.getEmail());
        user.setName(user1.getName());
        return user;
    }
}

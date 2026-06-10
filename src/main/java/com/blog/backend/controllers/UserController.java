package com.blog.backend.controllers;

import com.blog.backend.domain.dtos.response.UserResponseDto;
import com.blog.backend.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserDetails(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails){
        UserResponseDto user = userService.getUserData(id,userDetails.getUsername());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

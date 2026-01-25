package com.blog.backend.domain.dtos.response;

public class LoginResponse {
    private String message;
    private String  token;
    private UserResponseDto user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginResponse(String message, String token, UserResponseDto user) {
        this.message = message;
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

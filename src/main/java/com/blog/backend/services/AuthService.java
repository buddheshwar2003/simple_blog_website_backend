package com.blog.backend.services;

import com.blog.backend.JWT.JwtUtil;
import com.blog.backend.domain.dtos.*;
import com.blog.backend.domain.entities.RefreshToken;
import com.blog.backend.domain.entities.User;
import com.blog.backend.mapper.UserMapper;
import com.blog.backend.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final UserMapper userMapper;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil, RefreshTokenService refreshTokenService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        this.userMapper = userMapper;
    }

    public User register(RegisterRequest data){
        try {
            if (userRepository.findByEmail(data.getEmail()).isPresent()){
                throw new RuntimeException("User already exists");
            }

            User user = new User();
            user.setEmail(data.getEmail());
            user.setPassword(passwordEncoder.encode(data.getPassword()));
            user.setName(data.getName());

            return userRepository.save(user);

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 SHOW REAL ERROR
            throw e;
        }
    }


    public LoginResult login(LoginRequest req) {
    try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        String token = jwtUtil.getToken(user.getEmail());

        RefreshToken refreshToken = refreshTokenService.create(user.getEmail());

        ResponseCookie cookie = ResponseCookie.from("refreshToken",refreshToken.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/auth/refresh")
                .maxAge(7*24*60*60)
                .sameSite("Strict")
                .build();
        UserResponseDto userDetails = userMapper.toResponse(user);

        return new LoginResult(new LoginResponse("Login Successful", token,userDetails),cookie);

    } catch (Exception e) {
        e.printStackTrace(); // 🔥 SHOW REAL ERROR
        throw e;
    }
    }

    public LoginResponse refresh(String refreshToken) {

        if (refreshToken == null) {
            throw new RuntimeException("Refresh token missing");
        }

        // 1️⃣ Verify refresh token (DB + expiry)
        RefreshToken tokenEntity =
                refreshTokenService.verify(refreshToken);

        // 2️⃣ Load user
        User user = userRepository
                .findByEmail(tokenEntity.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3️⃣ Generate NEW access token
        String newAccessToken =
                jwtUtil.getToken(user.getEmail());

        UserResponseDto userDto =
                userMapper.toResponse(user);

        return new LoginResponse(
                "Token refreshed",
                newAccessToken,
                userDto
        );
    }

    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/auth");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

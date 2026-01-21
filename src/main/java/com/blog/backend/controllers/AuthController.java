package com.blog.backend.controllers;

import com.blog.backend.domain.dtos.LoginRequest;
import com.blog.backend.domain.dtos.LoginResponse;
import com.blog.backend.domain.dtos.LoginResult;
import com.blog.backend.domain.dtos.RegisterRequest;
import com.blog.backend.domain.entities.User;
import com.blog.backend.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "http://localhost:3000" , allowCredentials = "true")
@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<User> Register(@RequestBody RegisterRequest req){
        System.out.println(req.toString());
        return new ResponseEntity<>(authService.register(req), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> Login(@RequestBody LoginRequest req , HttpServletResponse res){
        LoginResult response = authService.login(req);
        res.addHeader(HttpHeaders.SET_COOKIE,response.getResponseCookie().toString());
        return new ResponseEntity<>(response.getLoginResponse(),HttpStatus.OK);
    }
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken) {

        LoginResponse response = authService.refresh(refreshToken);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.noContent().build();
    }
}

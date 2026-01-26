package com.blog.backend.controllers;

import com.blog.backend.domain.dtos.request.LoginRequest;
import com.blog.backend.domain.dtos.response.LoginResponse;
import com.blog.backend.domain.dtos.response.LoginResult;
import com.blog.backend.domain.dtos.request.RegisterRequest;
import com.blog.backend.domain.entities.User;
import com.blog.backend.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://simple-blog-website-frontend.vercel.app" , allowCredentials = "true")
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
    public ResponseEntity<Void> logout( @CookieValue(name = "refreshToken", required = false) String refreshToken ,HttpServletResponse response) {
        authService.logout(refreshToken,response);
        return ResponseEntity.noContent().build();
    }
}

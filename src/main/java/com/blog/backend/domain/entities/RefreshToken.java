package com.blog.backend.domain.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false , unique = true)
    private String token;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Instant expiryDate;

    public RefreshToken(Long id, String token, String email, Instant expiryDate) {
        this.id = id;
        this.token = token;
        this.email = email;
        this.expiryDate = expiryDate;
    }

    public RefreshToken() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
}

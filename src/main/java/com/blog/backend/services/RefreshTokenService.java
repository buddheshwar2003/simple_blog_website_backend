package com.blog.backend.services;

import com.blog.backend.domain.entities.RefreshToken;
import com.blog.backend.repositories.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository repo;

    public RefreshTokenService(RefreshTokenRepository repo) {
        this.repo = repo;
    }

    public RefreshToken create(String email){
        repo.deleteByEmail(email); //If you want to logout if another user login in another device

        RefreshToken token = new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setEmail(email);
        token.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));

        return repo.save(token);

    }

    public  RefreshToken verify(String token){
        RefreshToken refreshToken = repo.findByToken(token).orElseThrow(()->new RuntimeException("Invalid Refresh Token"));

        if(refreshToken.getExpiryDate().isBefore(Instant.now())){
            repo.delete(refreshToken);
            throw new RuntimeException("Refresh Token Expired");
        }
        return refreshToken;
    }

    public void delete(String token){
        repo.findByToken(token).ifPresent(repo::delete);
    }
}

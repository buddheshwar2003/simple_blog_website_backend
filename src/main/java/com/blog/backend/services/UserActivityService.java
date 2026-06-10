package com.blog.backend.services;

import com.blog.backend.domain.entities.User;
import com.blog.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class UserActivityService {
    private final UserRepository userRepository;

    private final Map<String, LocalDateTime> onlineUsers =
            new ConcurrentHashMap<>();

    public UserActivityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void userDisconnected(String email){

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        user.setLastSeen(LocalDateTime.now());

        userRepository.save(user);

        onlineUsers.remove(email);
    }

    public void userConnected(String email) {
        onlineUsers.put(email,LocalDateTime.now());
    }

    public boolean isOnline(String email) {
        return onlineUsers.containsKey(email);
    }

    public LocalDateTime lastSeen(String email) {
        return onlineUsers.get(email);
    }
}

package com.blog.backend.repositories;

import com.blog.backend.domain.entities.Likes;
import com.blog.backend.domain.entities.Posts;
import com.blog.backend.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Likes, UUID> {
    Optional<Likes> findByUserAndPost(
            User user,
            Posts post
    );
    boolean existsByUserAndPost(
            User user,
            Posts post
    );
}

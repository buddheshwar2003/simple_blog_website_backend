package com.blog.backend.repositories;

import com.blog.backend.domain.entities.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CommentRepository extends JpaRepository<Comments, UUID> {
    Page<Comments> findAllByPostId(UUID postId, Pageable pageable);
}

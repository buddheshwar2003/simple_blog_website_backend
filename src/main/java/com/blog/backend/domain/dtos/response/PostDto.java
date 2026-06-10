package com.blog.backend.domain.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostDto(
        UUID id,
        String username,
        UUID userId,
        String author,
        String title,
        String content,
        String category,
        LocalDateTime updatedAt,
        int likeCount,
        int commentCount,
        boolean likedByCurrentUser
) {

}


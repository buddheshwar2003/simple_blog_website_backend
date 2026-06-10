package com.blog.backend.domain.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageResponse(
        UUID id,
        UUID senderId,
        UUID receiverId,
        String senderName,
        String content,
        boolean seen,
        LocalDateTime createdAt
) {
}

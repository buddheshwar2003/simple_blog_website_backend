package com.blog.backend.domain.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChatMessageResponse(
        UUID messageId,
        UUID senderId,
        String senderName,
        String content,
        LocalDateTime createdAt
) {
}

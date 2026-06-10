package com.blog.backend.domain.dtos.request;

import java.util.UUID;

public record ChatMessageRequest(
        UUID receiverId,
        String message
) {
}

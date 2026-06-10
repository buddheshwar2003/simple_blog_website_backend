package com.blog.backend.domain.dtos.response;

public record NotificationSocketResponse(
        String type,
        String message,
        long unreadCount
) {
}

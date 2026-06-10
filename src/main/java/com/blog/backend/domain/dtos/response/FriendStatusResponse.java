package com.blog.backend.domain.dtos.response;

import java.util.UUID;

public record FriendStatusResponse(
        String status,
        UUID requestId
) {}

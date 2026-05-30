package com.blog.backend.domain.dtos.response;

import java.util.UUID;

public class CommentResponse {
    private String user;
    private String text;
    private UUID userId;

    public CommentResponse(String user, String text, UUID userId) {
        this.user = user;
        this.text = text;
        this.userId = userId;
    }

    public String getUser() {
        return user;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

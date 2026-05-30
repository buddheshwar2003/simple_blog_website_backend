package com.blog.backend.domain.dtos.request;

import java.util.UUID;

public class CommentRequest {
    private String comment;
    private UUID postId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }
}

package com.blog.backend.domain.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class BlogCreateUpdateResponse {
    private UUID id;
    private String title;
    private String content;
    private String category;
    private String authorName;
    private LocalDateTime lastUpdate;

    public BlogCreateUpdateResponse(UUID id, String title, String content, String category, String authorName, LocalDateTime lastUpdate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.authorName = authorName;
        this.lastUpdate = lastUpdate;
    }

    public BlogCreateUpdateResponse() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}

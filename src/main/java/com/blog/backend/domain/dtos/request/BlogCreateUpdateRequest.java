package com.blog.backend.domain.dtos.request;


public class BlogCreateUpdateRequest {
    private String title;
    private String content;
    private String categoryName;

    @Override
    public String toString() {
        return "BlogCreateUpdateRequest{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

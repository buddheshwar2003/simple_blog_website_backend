package com.blog.backend.domain.entities;

import java.time.LocalDateTime;

import java.util.Objects;

import java.util.UUID;

import com.blog.backend.domain.PostStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Posts {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

    public Posts() {

    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public Posts(UUID id, String title, String content, PostStatus status, User author, Category category, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.author = author;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PostStatus status;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id",nullable = false)
	private User author;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id",nullable = false)
	private Category category;
	@Column(nullable = false)
	private LocalDateTime createdAt;
	@Column(nullable = false)
	private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Posts posts = (Posts) o;
        return Objects.equals(id, posts.id) && Objects.equals(title, posts.title) && Objects.equals(content, posts.content) && status == posts.status && Objects.equals(author, posts.author) && Objects.equals(category, posts.category) && Objects.equals(createdAt, posts.createdAt) && Objects.equals(updatedAt, posts.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, status, author, category, createdAt, updatedAt);
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    public PostStatus getStatus() {
        return status;
    }



}

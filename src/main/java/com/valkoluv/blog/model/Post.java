package com.valkoluv.blog.model;

import java.time.LocalDateTime;

public class Post {
    private Long id;
    private String title;
    private String content;
    private String slug;
    private User author;
    private LocalDateTime createdAt;

    public Post(Long id, String title, String content, String slug, User author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.slug = slug;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
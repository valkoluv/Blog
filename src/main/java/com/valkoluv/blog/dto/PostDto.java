package com.valkoluv.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostDto {
    private Long id;

    @NotBlank(message = "Заголовок не може бути порожнім")
    @Size(min = 5, max = 100, message = "Заголовок має бути від 5 до 100 символів")
    private String title;

    @NotBlank(message = "Вміст не може бути порожнім")
    @Size(min = 20, message = "Вміст має бути не менше 20 символів")
    private String content;

    private Long authorId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
}

package com.valkoluv.blog.model;
public class User {
    private Long id;
    private String username;
    private String email;

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = username;
    }
}

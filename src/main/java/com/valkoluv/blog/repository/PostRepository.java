package com.valkoluv.blog.repository;

import com.valkoluv.blog.model.Post;
import com.valkoluv.blog.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private final Map<Long, Post> posts = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(3);

    public PostRepository() {
        User defaultAuthor = new User(1L, "DefaultAuthor");
        posts.put(1L, new Post(1L, "Перший Пост", "Це тестовий вміст для першого поста.", "pershyi-post", defaultAuthor));
        posts.put(2L, new Post(2L, "Другий Пост Про Java", "Ще більше вмісту, що стосується Java.", "druhyi-post-java", defaultAuthor));
    }

    public List<Post> findAll() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Optional<Post> findBySlug(String slug) {
        return posts.values().stream()
                .filter(post -> post.getSlug().equals(slug))
                .findFirst();
    }

    public Post save(Post post) {
        if (post.getId() == null) {
            post.setId(idCounter.incrementAndGet());
        }
        posts.put(post.getId(), post);
        return post;
    }

    public void deleteById(Long id) {
        posts.remove(id);
    }
}

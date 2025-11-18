package com.valkoluv.blog.repository;

import com.valkoluv. blog.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public UserRepository() {
        User defaultAuthor = new User(1L, "DefaultAuthor");
        this.users.put(1L, defaultAuthor);
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<User> findByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    public User save(User user) {
        if (user.getId() == null) {
            Long newId = idCounter.incrementAndGet();
            User newUser = new User(newId, user.getUsername());
            users.put(newId, newUser);
            return newUser;
        }
        users.put(user.getId(), user);
        return user;
    }

    public void deleteById(Long id) {
        users.remove(id);
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
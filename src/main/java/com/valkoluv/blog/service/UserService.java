package com.valkoluv.blog.service;

import com.valkoluv.blog.dto.UserDto;
import com.valkoluv.blog.model.User;
import com.valkoluv.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User registerOrUpdate(UserDto userDto) {
        if (userDto.getId() == null && userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("Користувач з таким іменем вже існує!");
        }

        User user;
        if (userDto.getId() != null) {
            user = userRepository.findById(userDto.getId())
                    .orElseThrow(() -> new RuntimeException("Користувач не знайдений для оновлення."));
            user.setUsername(userDto.getUsername());
        } else {
            user = new User(null, userDto.getUsername());
        }

        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}

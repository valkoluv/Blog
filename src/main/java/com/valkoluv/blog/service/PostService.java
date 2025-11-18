package com.valkoluv.blog.service;

import com.valkoluv.blog.dto.PostDto;
import com.valkoluv.blog.model.Post;
import com.valkoluv.blog.model.User;
import com.valkoluv.blog.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    private User getDefaultAuthor() {
        return new User(1L, "DefaultAuthor");
    }

    public Page<Post> findAllPaged(int page, int size) {
        List<Post> allPosts = postRepository.findAll().stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allPosts.size());

        List<Post> pageContent = (start <= end) ? allPosts.subList(start, end) : List.of();

        return new PageImpl<>(pageContent, pageable, allPosts.size());
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Optional<Post> findBySlug(String slug) {
        return postRepository.findBySlug(slug);
    }

    public Post save(PostDto postDto) {

        User author = userService.findById(postDto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Автор з ID " + postDto.getAuthorId() + " не знайдений!"));

        String slug = postDto.getTitle().toLowerCase().trim().replaceAll("[^a-z0-9\\s-]", "").replaceAll("[\\s]+", "-");

        Post post;
        if (postDto.getId() != null) {
            post = postRepository.findById(postDto.getId())
                    .orElseThrow(() -> new RuntimeException("Пост не знайдено для оновлення!"));

            post.setTitle(postDto.getTitle());
            post.setContent(postDto.getContent());
            post.setSlug(slug);
            post.setAuthor(author);
        } else {
            post = new Post(null, postDto.getTitle(), postDto.getContent(), slug, author);
        }

        return postRepository.save(post);
    }

    public void deleteById(Long id) {
        if (!postRepository.findById(id).isPresent()) {
            throw new RuntimeException("Пост з ID " + id + " не існує!");
        }
        postRepository.deleteById(id);
    }
}
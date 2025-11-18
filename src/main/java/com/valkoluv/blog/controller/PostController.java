package com.valkoluv.blog.controller;

import com.valkoluv.blog.dto.PostDto;
import com.valkoluv.blog.model.Post;
import com.valkoluv.blog.service.PostService;
import com.valkoluv.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private static final int PAGE_SIZE = 10;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    public String listPosts(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Post> postPage = postService.findAllPaged(page, PAGE_SIZE);
        model.addAttribute("postPage", postPage);
        model.addAttribute("currentPage", page);
        return "post/list";
    }

    @GetMapping("/{identifier}")
    public String viewPost(@PathVariable String identifier, Model model) {
        Optional<Post> post = Optional.empty();

        try {
            Long id = Long.parseLong(identifier);
            post = postService.findById(id);
        } catch (NumberFormatException e) {
            post = postService.findBySlug(identifier);
        }

        if (post.isEmpty()) {
            model.addAttribute("message", "Пост не знайдено!");
            return "error/404";
        }

        model.addAttribute("post", post.get());
        return "post/detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("postDto", new PostDto());
        model.addAttribute("isEdit", false);
        model.addAttribute("authors", userService.findAll());
        return "post/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Post post = postService.findById(id)
                .orElseThrow(() -> new RuntimeException("Пост для редагування не знайдено!"));

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());

        model.addAttribute("postDto", postDto);
        model.addAttribute("isEdit", true);
        model.addAttribute("authors", userService.findAll());
        return "post/form";
    }

    @PostMapping("/save")
    public String savePost(@Valid @ModelAttribute("postDto") PostDto postDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "post/form";
        }

        try {
            Post savedPost = postService.save(postDto);
            redirectAttributes.addFlashAttribute("successMessage", "Пост успішно збережено!");
            return "redirect:/posts/" + savedPost.getSlug();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Помилка при збереженні: " + e.getMessage());
            return "redirect:/posts/new";
        }
    }

    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            postService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Пост успішно видалено!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Помилка при видаленні: " + e.getMessage());
        }
        return "redirect:/posts";
    }
}
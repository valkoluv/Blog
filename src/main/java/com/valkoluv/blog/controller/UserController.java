package com.valkoluv.blog.controller;

import com.valkoluv.blog.dto.UserDto;
import com.valkoluv.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "user/registration_form";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserDto userDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "user/registration_form";
        }

        try {
            userService.registerOrUpdate(userDto);
            redirectAttributes.addFlashAttribute("successMessage", "Користувач успішно зареєстрований!");
            return "redirect:/login";
        } catch (RuntimeException e) {
            bindingResult.rejectValue("username", "error.user", e.getMessage());
            return "user/registration_form";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Користувача успішно видалено.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Помилка при видаленні користувача.");
        }
        return "redirect:/users";
    }
}

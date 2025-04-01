package com.example.PersonalBlog.controller;

import com.example.PersonalBlog.model.User;
import com.example.PersonalBlog.service.AuthService;
import com.example.PersonalBlog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup"; // Displays the signup form
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user) {
        // Pass username and password separately to the registerUser method
        userService.registerUser(user.getUsername(), user.getPassword());
        return "redirect:/auth/login"; // Redirect to login page after signup
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Displays the login form
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        if (authService.authenticate(email, password)) {
            return "redirect:/home"; // Redirect to home after login
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login"; // Stay on login page if authentication fails
        }
    }
}

package com.example.PersonalBlog.controller;

import com.example.PersonalBlog.model.User;
import com.example.PersonalBlog.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public LoginController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username, email, or password.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        Optional<User> optionalUser = userService.findByUsername(username);

        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }

        User user = optionalUser.get();

        // Check if the email matches
        if (!user.getEmail().equals(email)) {
            model.addAttribute("error", "Email does not match the registered username.");
            return "login";
        }

        if (userService.isAccountLocked(username)) {
            model.addAttribute("error", "Your account is locked due to too many failed login attempts.");
            return "login";
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            userService.resetFailedAttempts(user);
            return "redirect:/home";

        } catch (AuthenticationException e) {
            userService.increaseFailedAttempts(user);
            model.addAttribute("error", "Invalid username, email, or password.");
            return "login";
        }
    }
}

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
            model.addAttribute("error", "Invalid username or password.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model) {
        if (userService.isAccountLocked(username)) {
            model.addAttribute("error", "Your account is locked due to too many failed login attempts.");
            return "login";
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            User user = userService.findByUsername(username).orElseThrow();
            userService.resetFailedAttempts(user);
            return "redirect:/home";

        } catch (AuthenticationException e) {
            userService.findByUsername(username).ifPresent(userService::increaseFailedAttempts);
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }
    }
}

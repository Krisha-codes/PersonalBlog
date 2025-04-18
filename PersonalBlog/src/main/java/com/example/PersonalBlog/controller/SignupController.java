package com.example.PersonalBlog.controller;

import com.example.PersonalBlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";  // Renders signup.html
    }

    @PostMapping("/signup")
    public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String email, Model model) {
        userService.registerUser(username, password, email);
        model.addAttribute("message", "Signup successful!");
        return "success";  // Redirects to a success page
    }
}


package com.example.PersonalBlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Principal principal) {

        if (principal != null)
        {
            return "redirect:/home";
        }
        return "login";
    }
    @PostMapping("/login")
    public String processLogin() {
        // No authentication yet, just redirecting
        return "redirect:/home";
    }

}

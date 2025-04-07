package com.example.PersonalBlog.controller;

import com.example.PersonalBlog.model.User;
import com.example.PersonalBlog.repository.UserRepository;
import com.example.PersonalBlog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class ProfileController {
    private final UserRepository userRepository;
    private final UserService userService;

    public ProfileController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> recentActivity = userService.getRecentActivity(user);

        model.addAttribute("user", user);
        model.addAttribute("recentActivity", recentActivity);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam("bio") String bio,
                                @RequestParam("socialLinks") String socialLinks,
                                @RequestParam("profilePicture") MultipartFile profilePicture,
                                Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        userService.updateUserProfile(user, bio, socialLinks);

        try {
            userService.uploadProfilePicture(user, profilePicture);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/profile";
    }
}

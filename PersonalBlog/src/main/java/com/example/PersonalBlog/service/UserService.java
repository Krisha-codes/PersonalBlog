package com.example.PersonalBlog.service;

import com.example.PersonalBlog.model.User;
import com.example.PersonalBlog.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";
    private static final int MAX_FAILED_ATTEMPTS = 3;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Updated registerUser method to accept username and password separately
    public void registerUser(String username, String password) {
        // Create a new User object
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Encrypt password
        userRepository.save(user);  // Save the user to the database
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public void updateUserProfile(User user, String bio, String socialLinks) {
        user.setBio(bio);
        user.setSocialLinks(socialLinks);
        userRepository.save(user);
    }
    public String uploadProfilePicture(User user, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String filename = user.getUsername() + "_profile.png";
            Path filePath = Paths.get(UPLOAD_DIR + filename);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            // Save relative path so frontend can access it
            String imageUrl = "/uploads/" + filename;
            user.setProfilePicture(imageUrl);
            userRepository.save(user);
            return imageUrl; // Return correct URL
        }
        return null;
    }
    public List<String> getRecentActivity(User user) {
        // Placeholder for actual logic (e.g., fetching recent posts, comments, etc.)
        return List.of("Liked a post", "Commented on an article", "Updated profile");
    }


    public boolean isAccountLocked(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(User::isAccountLocked).orElse(false);
    }

    public void increaseFailedAttempts(User user) {
        int failedAttempts = user.getFailedAttempts() + 1;
        user.setFailedAttempts(failedAttempts);

        if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
            user.setAccountLocked(true);
        }

        userRepository.save(user);
    }

    public void resetFailedAttempts(User user) {
        user.setFailedAttempts(0);
        userRepository.save(user);
    }

    public void unlockUserAccount(User user) {
        user.setAccountLocked(false);
        user.setFailedAttempts(0);
        userRepository.save(user);
    }
}
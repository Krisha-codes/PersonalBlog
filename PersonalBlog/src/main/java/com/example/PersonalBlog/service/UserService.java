package com.example.PersonalBlog.service;
import com.example.PersonalBlog.model.User;
import com.example.PersonalBlog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void registerUser(String username, String password) {
        User user = new User(username, password);
        userRepository.save(user);
    }
}

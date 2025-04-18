package com.example.PersonalBlog.repository;

import com.example.PersonalBlog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email); // 🔹 New method

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}



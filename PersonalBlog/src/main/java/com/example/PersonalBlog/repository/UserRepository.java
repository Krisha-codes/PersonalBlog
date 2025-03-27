package com.example.PersonalBlog.repository;
//provides CRUD operations for User
import com.example.PersonalBlog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

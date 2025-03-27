package com.example.PersonalBlog.repository;

import com.example.PersonalBlog.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByUsername(String username);
}


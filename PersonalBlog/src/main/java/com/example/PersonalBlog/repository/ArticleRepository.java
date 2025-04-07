package com.example.PersonalBlog.repository;

import com.example.PersonalBlog.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByUsername(String username);
    //Fetch top 3 articles sorted by newest first
    @Query("SELECT a FROM Article a WHERE a.username = :username ORDER BY a.id DESC LIMIT 3")
    List<Article> findTop3ByUsername(String username);
}




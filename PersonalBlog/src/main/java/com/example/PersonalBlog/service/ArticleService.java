package com.example.PersonalBlog.service;

import com.example.PersonalBlog.model.Article;
import com.example.PersonalBlog.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getUserArticles(String username) {
        return articleRepository.findByUsername(username);
    }
}

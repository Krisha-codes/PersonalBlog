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

    //Save a new article
    public void saveArticle(String username, String title, String content) {
        Article article = new Article();
        article.setUsername(username);
        article.setTitle(title);
        article.setContent(content);
        articleRepository.save(article);
    }

    //Get the top 3 articles by title (truncate if >10 letters)
    public List<Article> getTopArticles(String username) {
        List<Article> articles = articleRepository.findByUsername(username);
        return articles.stream()
                .limit(3) // Get only top 3 articles
                .toList();
    }

    //Get all articles for "Show More"
    public List<Article> getAllArticles(String username) {
        return articleRepository.findByUsername(username);
    }
}

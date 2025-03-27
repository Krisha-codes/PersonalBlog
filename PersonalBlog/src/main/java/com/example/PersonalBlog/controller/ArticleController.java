package com.example.PersonalBlog.controller;

import com.example.PersonalBlog.model.Article;
import com.example.PersonalBlog.repository.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/new-article")
    public String newArticlePage() {
        return "new-article";
    }

    @PostMapping("/new-article")
    public String createArticle(@RequestParam String title,
                                @RequestParam String content,
                                Principal principal) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setUsername(principal.getName()); // Get logged-in user

        articleRepository.save(article);
        return "redirect:/home";
    }
}

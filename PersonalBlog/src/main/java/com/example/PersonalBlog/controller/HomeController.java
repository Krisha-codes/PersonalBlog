package com.example.PersonalBlog.controller;

import com.example.PersonalBlog.model.Article;
import com.example.PersonalBlog.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private final ArticleService articleService;

    public HomeController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/home")
    public String homePage(Model model, Principal principal) {
        // Get the logged-in username
        String username = principal.getName();
        model.addAttribute("username", username);

        // Fetch user's articles
        List<Article> articles = articleService.getUserArticles(username);
        model.addAttribute("articles", articles);

        return "home"; // Loads home.html
    }
}


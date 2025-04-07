package com.example.PersonalBlog.controller;

import com.example.PersonalBlog.model.Article;
import com.example.PersonalBlog.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final ArticleService articleService;

    public HomeController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String homePage(Model model, Principal principal) {
        // Get the logged-in username
        String username = principal.getName();
        model.addAttribute("username", username);

        // Fetch top 3 articles
        List<Article> topArticles = articleService.getTopArticles(username);
        model.addAttribute("topArticles", topArticles);

        return "home"; // Loads home.html
    }

    //Save new article
    @PostMapping("/save-article")
    public String saveArticle(@RequestParam String title,
                              @RequestParam String content,
                              Principal principal) {
        String username = principal.getName();
        articleService.saveArticle(username, title, content);
        return "redirect:/home"; // Reload home after saving
    }

    //Fetch all articles when "Show More" is clicked
    @GetMapping("/all-articles")
    @ResponseBody
    public List<Article> getAllArticles(Principal principal) {
        return articleService.getAllArticles(principal.getName());
    }
}

package org.example.javaspring.domain.article.controller;

import lombok.RequiredArgsConstructor;
import org.example.javaspring.domain.article.ArticleService;
import org.example.javaspring.domain.article.command.CreateArticle;
import org.example.javaspring.domain.article.command.SearchCondition;
import org.example.javaspring.domain.article.command.UpdateContent;
import org.example.javaspring.domain.article.command.UpdateStatus;
import org.example.javaspring.domain.article.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable Long id) {
        return articleService.getArticle(id);
    }

    @GetMapping
    public List<Article> listArticles() {
        return articleService.listArticles();
    }

    @PostMapping
    public void createArticle(@RequestBody CreateArticle createArticle) {
        articleService.createArticle(createArticle);
    }

    @PutMapping("/{id}/content")
    public void updateContent(@PathVariable Long id, @RequestBody UpdateContent updateContent) {
        updateContent.setId(id);
        articleService.updateContent(updateContent);
    }

    @PutMapping("/{id}/status")
    public void updateStatus(@PathVariable Long id, @RequestBody UpdateStatus updateStatus) {
        updateStatus.setId(id);
        articleService.updateStatus(updateStatus);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        articleService.delete(id);
    }

    @GetMapping("/count")
    public int countAll() {
        return articleService.countAll();
    }

    @GetMapping("/search")
    public List<Article> findBySearchCondition(@ModelAttribute SearchCondition searchCondition) {
        return articleService.findBySearchCondition(searchCondition);
    }

    @GetMapping("/search/count")
    public int countBySearchCondition(@ModelAttribute SearchCondition searchCondition) {
        return articleService.countBySearchCondition(searchCondition);
    }
}


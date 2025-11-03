package org.example.javaspring.domain.article.repository;

import org.example.javaspring.domain.article.command.SearchCondition;
import org.example.javaspring.domain.article.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Optional<Article> findById(Long id);
    List<Article> findAll();
    List<Article> findBySearchCondition(SearchCondition searchCondition);
    int create(Article article);
    int updateContent(Article article);
    int updateStatus(Article article);
    int delete(Long id);
    int countAll();
    int countBySearchCondition(SearchCondition searchCondition);
}

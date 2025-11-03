package org.example.javaspring.domain.article.repository;

import org.example.javaspring.domain.article.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleJpaRepository extends JpaRepository<Article, Long> {
}

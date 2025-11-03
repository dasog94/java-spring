package org.example.javaspring.domain.article.repository;

import lombok.RequiredArgsConstructor;
import org.example.javaspring.domain.article.command.SearchCondition;
import org.example.javaspring.domain.article.model.Article;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepository {
    private final ArticleMapper articleMapper;

    @Override
    public Optional<Article> findById(Long id) {
        return articleMapper.findById(id);
    }

    @Override
    public List<Article> findAll() {
        return articleMapper.findAll();
    }

    @Override
    public List<Article> findBySearchCondition(SearchCondition searchCondition) {
        return articleMapper.findBySearchCondition(searchCondition);
    }

    @Override
    public int create(Article article) {
        return articleMapper.create(article);
    }

    @Override
    public int updateContent(Article article) {
        return articleMapper.updateContent(article);
    }

    @Override
    public int updateStatus(Article article) {
        return articleMapper.updateStatus(article);
    }

    @Override
    public int delete(Long id) {
        return articleMapper.delete(id);
    }

    @Override
    public int countAll() {
        return articleMapper.countAll();
    }

    @Override
    public int countBySearchCondition(SearchCondition searchCondition) {
        return articleMapper.countBySearchCondition(searchCondition);
    }
}

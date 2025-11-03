package org.example.javaspring.domain.article;

import lombok.RequiredArgsConstructor;
import org.example.javaspring.domain.article.command.CreateArticle;
import org.example.javaspring.domain.article.command.SearchCondition;
import org.example.javaspring.domain.article.command.UpdateContent;
import org.example.javaspring.domain.article.command.UpdateStatus;
import org.example.javaspring.domain.article.model.Article;
import org.example.javaspring.domain.article.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article getArticle(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Article with id " + id + " not found"));
    }

    public List<Article> listArticles() {
        return articleRepository.findAll();
    }

    public List<Article> findBySearchCondition(SearchCondition searchCondition) {
        return articleRepository.findBySearchCondition(searchCondition);
    }

    public void createArticle(CreateArticle createArticle) {
        Article article = createArticle.toModel();

        int num = articleRepository.create(article);
        if (num != 1) {
            throw new IllegalStateException("Failed to create Article: return value=" + num);
        }
    }

    public void updateContent(UpdateContent updateContent) {
        Article article = articleRepository.findById(updateContent.getId())
                .orElseThrow(
                        () -> new NoSuchElementException("Article with id " + updateContent.getId() + " not found")
                );

        article.updateContent(
                updateContent.getTitle(),
                updateContent.getContent()
        );

        int num = articleRepository.updateContent(article);
        if (num != 1) {
            throw new IllegalStateException("Failed to update Article: return value=" + num);
        }
    }

    public void updateStatus(UpdateStatus updateStatus) {
        Article article = articleRepository.findById(updateStatus.getId())
                .orElseThrow(
                        () -> new NoSuchElementException("Article with id " + updateStatus.getId() + " not found")
                );

        article.updateStatus(
                updateStatus.getVisible()
        );

        int num = articleRepository.updateStatus(article);
        if (num != 1) {
            throw new IllegalStateException("Failed to update Article status: return value=" + num);
        }
    }

    public void delete(Long id) {
        int num = articleRepository.delete(id);
        if (num != 1) {
            throw new IllegalStateException("Failed to delete Article: return value=" + num);
        }
    }

    public int countAll() {
        return articleRepository.countAll();
    }

    public int countBySearchCondition(SearchCondition searchCondition) {
        return articleRepository.countBySearchCondition(searchCondition);
    }
}

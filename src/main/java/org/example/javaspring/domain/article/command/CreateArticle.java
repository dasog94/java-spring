package org.example.javaspring.domain.article.command;

import lombok.Builder;
import lombok.Getter;
import org.example.javaspring.domain.article.model.Article;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateArticle {
    private String title;
    private String content;
    private String author;

    public Article toModel() {
        return Article.builder()
                .title(this.title)
                .content(this.content)
                .author(this.author)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}

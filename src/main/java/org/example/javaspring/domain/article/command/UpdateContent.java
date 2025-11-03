package org.example.javaspring.domain.article.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.javaspring.domain.article.model.Article;

import java.time.LocalDateTime;

@Getter
@Builder
public class UpdateContent {
    @Setter
    private Long id;
    private String title;
    private String content;

    public Article toModel() {
        return Article.builder()
                .title(this.title)
                .content(this.content)
                .updatedAt(LocalDateTime.now())
                .build();
    }
}

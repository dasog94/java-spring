package org.example.javaspring.domain.article.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class UpdateStatus {
    @Setter
    private Long id;
    private Boolean visible;
}

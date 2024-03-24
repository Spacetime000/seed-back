package com.SeedOasis.quizzes;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizDto {

    private Long id;
    private String title;
    private String category;
    private LocalDateTime createdDate;
    private String createBy;
    private Boolean visibility;

    public static QuizDto of(Quiz quiz) {
        return QuizDto.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .category(quiz.getQuizCategory().getName())
                .createdDate(quiz.getCreatedDate())
                .createBy(quiz.getCreateBy())
                .visibility(quiz.getVisibility())
                .build();
    }
}

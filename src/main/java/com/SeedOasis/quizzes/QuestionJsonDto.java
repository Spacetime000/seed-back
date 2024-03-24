package com.SeedOasis.quizzes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionJsonDto {
    private String question;
    private String category;
    private String answer;
    private List<String> options;
    private String img;
}

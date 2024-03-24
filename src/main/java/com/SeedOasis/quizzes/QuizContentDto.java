package com.SeedOasis.quizzes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizContentDto {

    private Long id;
    private String title;
    private String category;
    private List<QuestionJsonDto> questions;
    private String createBy;

    public static QuizContentDto of(Quiz quiz) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<QuestionJsonDto> questionJsonDtos;
            if (quiz.getQuestions() != null) {
                questionJsonDtos = objectMapper.readValue(quiz.getQuestions(), new TypeReference<List<QuestionJsonDto>>() {
                });
            } else {
                questionJsonDtos = null;
            }

            return QuizContentDto.builder()
                    .id(quiz.getId())
                    .title(quiz.getTitle())
                    .category(quiz.getQuizCategory().getName())
                    .questions(questionJsonDtos)
                    .createBy(quiz.getCreateBy())
                    .build();
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

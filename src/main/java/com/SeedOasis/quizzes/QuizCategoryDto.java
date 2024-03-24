package com.SeedOasis.quizzes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizCategoryDto {

    private String name;

    public static QuizCategoryDto of(QuizCategory quizCategory) {
        return new QuizCategoryDto(quizCategory.getName());
    }
}

package com.SeedOasis.quizzes;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizCreateDto {

//    private List<QuizDto> quizzes = new ArrayList<>();

    @NotBlank
    private String title;

    @NotBlank
    private String category;
}

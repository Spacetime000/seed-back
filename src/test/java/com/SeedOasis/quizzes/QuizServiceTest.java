package com.SeedOasis.quizzes;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuizServiceTest {

    @Autowired
    QuizService quizService;

    @Test
    @DisplayName("임시")
    void temp() throws JsonProcessingException {
        String name = "test123123";
        quizService.deleteQuiz(7L, name);
    }
}
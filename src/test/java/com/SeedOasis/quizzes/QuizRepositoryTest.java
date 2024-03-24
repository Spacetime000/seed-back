package com.SeedOasis.quizzes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuizRepositoryTest {

    @Autowired
    QuizRepository quizRepository;

    @Test
    @DisplayName("임시")
    void temp() {

    }

}
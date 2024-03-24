package com.SeedOasis.quizzes;

import java.util.List;

public interface QuizRepositoryCustom {

    List<Quiz> visibleList();

    List<Quiz> findByMemberAndCategoryAndSearch(String name, String search, String category);

    List<Quiz> searchVisiable(String title, String category);
}

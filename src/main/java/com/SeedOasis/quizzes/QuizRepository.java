package com.SeedOasis.quizzes;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long>, QuizRepositoryCustom {

    @Query("SELECT qz.questions FROM Quiz qz WHERE qz.id = :id")
    String findByIdQuestions(@Param("id") Long id);

    @Query("SELECT qz FROM Quiz qz WHERE qz.createBy = :createBy ORDER BY qz.createdDate DESC")
    List<Quiz> findAllByMember(@Param("createBy") String createBy);

    Optional<Quiz> findByIdAndCreateBy(Long id, String createBy);

}

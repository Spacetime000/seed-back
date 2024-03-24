package com.SeedOasis.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    Optional<ReviewEntity> findByIdAndCreateBy(Long id, String createBy);

    List<ReviewEntity> findByCreateByOrderByReviewDateAsc(String createBy);

    List<ReviewEntity> findByCreateByAndReviewDateBetween(String createBy, LocalDate reviewDateStart, LocalDate reviewDateEnd);

    List<ReviewEntity> findByCreateByAndReviewDateLessThanEqualOrderByReviewDateDesc(String createBy, LocalDate localDate);

    List<ReviewEntity> findByCreateByAndReviewDateOrderByVocaTitle(String name, LocalDate localDate);
}

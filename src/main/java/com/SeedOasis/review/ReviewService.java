package com.SeedOasis.review;

import com.SeedOasis.voca.VocaContentDto;
import com.SeedOasis.voca.VocaEntity;
import com.SeedOasis.voca.VocaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final VocaRepository vocaRepository;

    public void create(ReviewResponseDto reviewResponseDto) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        VocaEntity vocaEntity = vocaRepository.findById(reviewResponseDto.getId()).orElseThrow();

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setContents(vocaEntity.getContents());
        reviewEntity.setVocaTitle(vocaEntity.getTitle());

        LocalDate localDate = LocalDate.now();
        reviewEntity.setReviewDate(localDate.plusDays(reviewResponseDto.getDay()));
        reviewRepository.save(reviewEntity);
    }

    public void delete(Long id, String name) {
        ReviewEntity reviewEntity = reviewRepository.findByIdAndCreateBy(id, name).orElseThrow();
        reviewRepository.delete(reviewEntity);
    }

    @Transactional
    public void update(ReviewResponseDto reviewResponseDto) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        ReviewEntity reviewEntity = reviewRepository.findByIdAndCreateBy(reviewResponseDto.getId(), name).orElseThrow();
        LocalDate localDate = LocalDate.now();
        reviewEntity.setReviewDate(localDate.plusDays(reviewResponseDto.getDay()));
        reviewEntity.setCount(reviewEntity.getCount() + 1);
    }

    public ReviewDto findByIdAndCreateBy(Long id, String name) throws JsonProcessingException {
        ReviewEntity reviewEntity = reviewRepository.findByIdAndCreateBy(id, name).orElseThrow();
        return ReviewDto.of(reviewEntity);
    }

    public List<ReviewDto> findByCreateByOrderByReviewDateAsc(String name) throws JsonProcessingException {
        List<ReviewEntity> list = reviewRepository.findByCreateByOrderByReviewDateAsc(name);
        List<ReviewDto> reviewDtoList = new ArrayList<>();

        for (ReviewEntity reviewEntity : list) {
            reviewDtoList.add(ReviewDto.of(reviewEntity));
        }
        return reviewDtoList;
    }

    public List<ReviewDto> getPriorToToday() throws JsonProcessingException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        LocalDate today = LocalDate.now();
        List<ReviewEntity> list = reviewRepository.findByCreateByAndReviewDateLessThanEqualOrderByReviewDateDesc(name, today);
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (ReviewEntity reviewEntity : list) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setId(reviewEntity.getId());
            reviewDto.setCount(reviewEntity.getCount());
            reviewDto.setReviewDate(reviewEntity.getReviewDate());
            reviewDto.setVocaTitle(reviewEntity.getVocaTitle());
            reviewDto.setCreatedDate(reviewEntity.getCreatedDate());
            reviewDto.setCreateBy(reviewEntity.getCreateBy());

            List<VocaContentDto> contents = objectMapper.readValue(reviewEntity.getContents(), new TypeReference<List<VocaContentDto>>() {
            });

            reviewDto.setContents(contents);
            reviewDtoList.add(reviewDto);
        }

        return reviewDtoList;

    }

    public List<ReviewDto> toDay() throws JsonProcessingException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ReviewEntity> entityList = reviewRepository.findByCreateByAndReviewDateOrderByVocaTitle(name, LocalDate.now());
        List<ReviewDto> dtoList = new ArrayList<>();

        for (ReviewEntity entity : entityList) {
            dtoList.add(ReviewDto.of(entity));
        }

        return dtoList;

    }
}

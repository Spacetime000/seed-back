package com.SeedOasis.review;

import com.SeedOasis.voca.VocaContentDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReviewDto {

    private Long id;
    private Long count;
    private List<VocaContentDto> contents;
    private LocalDate reviewDate;
    private String vocaTitle;
    private LocalDateTime createdDate;
    private String createBy;

    public static ReviewDto of(ReviewEntity reviewEntity) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(reviewEntity.getId());
        reviewDto.setCount(reviewEntity.getCount());
        reviewDto.setReviewDate(reviewEntity.getReviewDate());
        reviewDto.setVocaTitle(reviewEntity.getVocaTitle());
        reviewDto.setCreatedDate(reviewEntity.getCreatedDate());
        reviewDto.setCreateBy(reviewEntity.getCreateBy());

        List<VocaContentDto> contentList = objectMapper.readValue(reviewEntity.getContents(), new TypeReference<List<VocaContentDto>>() {
        });

        reviewDto.setContents(contentList);
        return reviewDto;
    }
}

package com.SeedOasis.review;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<?> readAll() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            List<ReviewDto> list = reviewService.findByCreateByOrderByReviewDateAsc(name);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("today")
    public ResponseEntity<?> today() {
        try {
            List<ReviewDto> day = reviewService.toDay();
            return ResponseEntity.ok(day);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ReviewResponseDto reviewResponseDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            reviewService.create(reviewResponseDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping
    public ResponseEntity<?> update(@Valid @RequestBody ReviewResponseDto reviewResponseDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            reviewService.update(reviewResponseDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            reviewService.delete(id, name);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("{id}")
    public ResponseEntity<?> read(@PathVariable("id") Long id) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            ReviewDto reviewDto = reviewService.findByIdAndCreateBy(id, name);
            return ResponseEntity.ok(reviewDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



    @GetMapping("priorToToday")
    public ResponseEntity<?> priorToToday() {
        try {
            List<ReviewDto> priorToToday = reviewService.getPriorToToday();
            return ResponseEntity.ok(priorToToday);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

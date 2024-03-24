package com.SeedOasis.quizzes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false, value = "search") String search,
                                  @RequestParam(required = false, value = "category") String category) {
        List<QuizDto> quizDtoList = quizService.visibleList(search, category);
        return ResponseEntity.ok(quizDtoList);
    }

    @GetMapping("my-page")
    public ResponseEntity<List<QuizDto>> myPage(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "category", defaultValue = "") String category
    ) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(search);
        System.out.println(category);
        List<QuizDto> list = quizService.findByMemberAndCategoryAndSearch(name, category, search);
        return ResponseEntity.ok(list);
    }

    /*
    @GetMapping
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page) {
        List<QuizDto> quizDtoList = quizService.visibleList(page);
        return ResponseEntity.ok(quizDtoList);
    }
     */

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody QuizCreateDto quizCreateDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> map = new HashMap<>();
            bindingResult.getFieldErrors().forEach(err -> {
                map.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(map);
        }

        try {
            Map<String, Long> map = new HashMap<>();
            Long id = quizService.createQuiz(quizCreateDto);
            map.put("id", id);
            return ResponseEntity.ok(map);
        } catch (NoSuchElementException e) {
            Map<String, String> map = new HashMap<>();
            map.put("message", "목록에 들어있는 카테고리만 가능합니다.");
            return ResponseEntity.badRequest().body(map);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    //?
    @GetMapping("{id}")
    public ResponseEntity<QuizContentDto> readQuiz(@PathVariable("id") Long id) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            QuizContentDto dto = quizService.findById(id, name);
            return ResponseEntity.ok(dto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("{id}")
    public ResponseEntity<List<QuestionJsonDto>> addQuestion(QuestionDto questionDto, @PathVariable("id") Long id) {
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            List<QuestionJsonDto> questionList = quizService.addQuestion(questionDto, id, name);
            return ResponseEntity.ok(questionList);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(403).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("category")
    public ResponseEntity<List<QuizCategoryDto>> readCategory() {
        return ResponseEntity.ok(quizService.findAllCategory());
    }

    @PostMapping("category")
    public ResponseEntity<?> createCategory(@RequestBody QuizCategoryDto quizCategoryDto) {
        quizService.createCategory(quizCategoryDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("category/{name}")
    public ResponseEntity<?> deleteCategory(@PathVariable("name") String name) {
        try {
            quizService.deleteCategory(name);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("{id}/toggle-visibility")
    public ResponseEntity<?> toggleVisibility(@PathVariable("id") Long id) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        quizService.toggleVisibility(id, name);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable("id") Long id) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            quizService.deleteQuiz(id, name);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

}

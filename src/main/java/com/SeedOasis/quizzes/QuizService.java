package com.SeedOasis.quizzes;

import com.SeedOasis.utils.FileService;
import com.SeedOasis.utils.UploadFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizCategoryRepository quizCategoryRepository;
    private final FileService fileService;

    private final static String PATH = "/quiz";

    @Transactional
    public List<QuizDto> visibleList(String search, String category) {
//        List<Quiz> quizzes = quizRepository.visibleList();
        List<Quiz> quizzes = quizRepository.searchVisiable(search, category);

        List<QuizDto> quizDtoList = new ArrayList<>();

        for (Quiz quiz : quizzes) {
            quizDtoList.add(QuizDto.of(quiz));
        }

        return quizDtoList;
    }

    public void createCategory(QuizCategoryDto quizCategoryDto) {
        QuizCategory quizCategory = new QuizCategory(quizCategoryDto.getName());
        quizCategoryRepository.save(quizCategory);
    }

    @Transactional
    public void deleteCategory(String name) {
        quizCategoryRepository.deleteById(name);
    }

    @Transactional
    public Long createQuiz(QuizCreateDto quizCreateDto) {
        QuizCategory quizCategory = quizCategoryRepository.findById(quizCreateDto.getCategory()).orElseThrow();
        Quiz quiz = new Quiz();
        quiz.setTitle(quizCreateDto.getTitle());
        quiz.setQuizCategory(quizCategory);
        quizRepository.save(quiz);
        return quiz.getId();
    }

    public QuizContentDto findById(Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        return QuizContentDto.of(quiz);
    }

    public QuizContentDto findById(Long id, String name) {
        Quiz quiz = quizRepository.findById(id).orElseThrow();

        if (quiz.getVisibility() || quiz.getCreateBy().equals(name)) {
            return QuizContentDto.of(quiz);
        } else {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }
    }

    public List<QuizDto> findByMemberAndCategoryAndSearch(String name, String category, String search) {
//        List<Quiz> all = quizRepository.findAllByMember(name);
        List<Quiz> all = quizRepository.findByMemberAndCategoryAndSearch(name, search, category);
        List<QuizDto> quizDtoList = new ArrayList<>();

        for (Quiz quiz : all) {
            quizDtoList.add(QuizDto.of(quiz));
        }
        return quizDtoList;
    }

    @Transactional
    public List<QuestionJsonDto> addQuestion(QuestionDto questionDto, Long id, String name) throws IOException, AccessDeniedException {
        Quiz quiz = quizRepository.findById(id).orElseThrow();

        if (!quiz.getCreateBy().equals(name)) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<QuestionJsonDto> questionDtoList;
        QuestionJsonDto questionJsonDto = QuestionJsonDto.builder()
                .question(questionDto.getQuestion())
                .category(questionDto.getCategory())
                .answer(questionDto.getAnswer())
                .options(questionDto.getOptions())
                .build();

        if (questionDto.getImg() != null) {
            UploadFile upload = fileService.upload(questionDto.getImg(), PATH);
            questionJsonDto.setImg(upload.getFilePath());
        }

        if (quiz.getQuestions() != null ) {
            questionDtoList = objectMapper.readValue(quiz.getQuestions(), new TypeReference<List<QuestionJsonDto>>(){});
        } else {
            questionDtoList = new ArrayList<>();
        }

        questionDtoList.add(questionJsonDto);
        quiz.setQuestions(objectMapper.writeValueAsString(questionDtoList));

        return questionDtoList;
    }

    public List<QuizCategoryDto> findAllCategory() {
        List<QuizCategory> all = quizCategoryRepository.findAll();
        List<QuizCategoryDto> dtoList = new ArrayList<>();

        for (QuizCategory qc : all) {
            dtoList.add(QuizCategoryDto.of(qc));
        }

        return dtoList;
    }

    @Transactional
    public void toggleVisibility(Long id, String name) {
        Quiz quiz = quizRepository.findByIdAndCreateBy(id, name).orElseThrow();
        quiz.setVisibility(!quiz.getVisibility());
    }

    public void deleteQuiz(Long id, String name) throws JsonProcessingException {
        Quiz quiz = quizRepository.findByIdAndCreateBy(id, name).orElseThrow();
        ObjectMapper objectMapper = new ObjectMapper();

        //이미지 제거
        if (quiz.getQuestions() != null) {
            List<QuestionJsonDto> questionJsonDtoList = objectMapper.readValue(quiz.getQuestions(), new TypeReference<List<QuestionJsonDto>>(){});

            for (QuestionJsonDto questionJsonDto : questionJsonDtoList) {
                if (questionJsonDto.getImg() != null) {
                    fileService.delete(questionJsonDto.getImg());
                }
            }
        }

        quizRepository.delete(quiz);

    }
}

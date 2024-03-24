package com.SeedOasis.quizzes;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class QuizRepositoryCustomImpl implements QuizRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private BooleanExpression eqSearch(String search) {
        if (StringUtils.hasText(search)) {
            return QQuiz.quiz.title.like("%" + search + "%");
        } else return null;
    }

    private BooleanExpression eqCategory(String category) {
        if (StringUtils.hasText(category)) {
            return QQuiz.quiz.quizCategory.name.eq(category);
        } else return null;
    }

    @Override
    public List<Quiz> visibleList() {
        return jpaQueryFactory.selectFrom(QQuiz.quiz)
                .where(QQuiz.quiz.visibility.eq(true))
                .orderBy(QQuiz.quiz.createdDate.desc())
                .fetch();
    }

    @Override
    public List<Quiz> findByMemberAndCategoryAndSearch(String name, String search, String category) {
        return jpaQueryFactory.selectFrom(QQuiz.quiz)
                .where(QQuiz.quiz.createBy.eq(name), eqCategory(category), eqSearch(search))
                .orderBy(QQuiz.quiz.createdDate.desc())
                .fetch();
    }

    @Override
    public List<Quiz> searchVisiable(String title, String category) {
        return jpaQueryFactory.selectFrom(QQuiz.quiz)
                .where(eqSearch(title), eqCategory(category), QQuiz.quiz.visibility.eq(true))
                .orderBy(QQuiz.quiz.createdDate.desc())
                .fetch();

    }
}

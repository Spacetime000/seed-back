package com.SeedOasis.quizzes;

import com.SeedOasis.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@TableGenerator(
        name = "QUIZ_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "QUIZ_SEQ",
        allocationSize = 1
)
public class Quiz extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "QUIZ_SEQ_GENERATOR")
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "category")
    private QuizCategory quizCategory;

    private Boolean visibility = false;

    @JdbcTypeCode(SqlTypes.JSON)
    private String questions;
}

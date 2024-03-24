package com.SeedOasis.review;

import com.SeedOasis.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Table(name = "review")
@NoArgsConstructor
@TableGenerator(
        name = "REVIEW_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "REVIEW_SEQ",
        allocationSize = 1
)
public class ReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "REVIEW_SEQ_GENERATOR")
    private Long id;

    private Long count = 1L;

    @JdbcTypeCode(SqlTypes.JSON)
    private String contents;

    private LocalDate reviewDate;
    private String vocaTitle;

}

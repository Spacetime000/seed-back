package com.SeedOasis.voca;

import com.SeedOasis.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "voca")
@ToString
@Getter @Setter
@NoArgsConstructor
@TableGenerator(
        name = "VOCA_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "VOCA_SEQ",
        allocationSize = 1
)
public class VocaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "VOCA_SEQ_GENERATOR")
    private Long id;

    private String title;
    private Boolean visibility = false;


    @JdbcTypeCode(SqlTypes.JSON)
    private String contents;

}

package my.playground.orm.entities.sub;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.playground.orm.domain.Grade;
import my.playground.orm.entities.UserEntity;

@Entity
@Data
@NoArgsConstructor
public class RecommendationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recommendationId;

    private Grade grade;

    @OneToOne
    private UserEntity source;

    private String comment;

    public RecommendationEntity(final Grade grade, final UserEntity source, final String comment) {
        this.grade = grade;
        this.source = source;
        this.comment = comment;
    }
}

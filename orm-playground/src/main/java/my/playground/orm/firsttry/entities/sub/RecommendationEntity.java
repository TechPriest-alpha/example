package my.playground.orm.firsttry.entities.sub;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.playground.orm.firsttry.domain.Grade;
import my.playground.orm.firsttry.entities.Client;

@Entity
@Data
@NoArgsConstructor
public class RecommendationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recommendationId;

    private Grade grade;

    @OneToOne
    private Client source;

    private String comment;

    public RecommendationEntity(final Grade grade, final Client source, final String comment) {
        this.grade = grade;
        this.source = source;
        this.comment = comment;
    }
}

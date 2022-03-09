package my.playground.orm.entities.sub;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.playground.orm.entities.UserEntity;
import my.playground.orm.events.Recommendation;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssistantEntity {
    @Id
    private Long assistanceId;

    @OneToOne
    private UserEntity userEntity;

    private String helperName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RecommendationEntity> recommendations;

    public UserId getId() {
        return userEntity.getId();
    }

    public String getName() {
        return userEntity.getName();
    }

    public void addRecommendation(final Recommendation recommendation) {
        recommendations.add(new RecommendationEntity(recommendation.grade(), new UserEntity(recommendation.clientId()), recommendation.comment()));
    }
}

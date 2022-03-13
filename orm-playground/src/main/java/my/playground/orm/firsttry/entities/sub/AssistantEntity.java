package my.playground.orm.firsttry.entities.sub;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.playground.orm.firsttry.entities.Client;
import my.playground.orm.firsttry.entities.UserEntity;
import my.playground.orm.firsttry.events.Recommendation;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class AssistantEntity {
    @Id
    private Long assistanceId;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private UserEntity userEntity;

    private String helperName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RecommendationEntity> recommendations;

    @Version
    private Long version;

    public AssistantEntity(final Long assistanceId, final UserEntity userEntity, final String helperName, final List<RecommendationEntity> recommendations) {
        this.assistanceId = assistanceId;
        this.userEntity = userEntity;
        this.helperName = helperName;
        this.recommendations = recommendations;
    }

    public UserId getId() {
        return userEntity.getId();
    }

    public String getName() {
        return userEntity.getName();
    }

    public void addRecommendation(final Recommendation recommendation) {
        recommendations.add(new RecommendationEntity(recommendation.grade(), new Client(recommendation.clientId()), recommendation.comment()));
    }
}

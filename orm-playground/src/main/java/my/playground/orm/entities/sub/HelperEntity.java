package my.playground.orm.entities.sub;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.playground.orm.entities.UserEntity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelperEntity {
    @Id
    private Long helperId;

    @OneToOne
    private UserEntity userEntity;

    private String helperName;
}

package my.playground.orm.secondtry.entities.hierarchy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("parent")
public class JoinTableParent {
    @Id
    @GeneratedValue
    private Long id;

    private String parentData;

    public JoinTableParent(final String parentData) {
        this.parentData = parentData;
    }
}

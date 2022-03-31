package my.playground.orm.secondtry.entities.hierarchy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("parent")
public class SingleTableParent {
    @Id
    @GeneratedValue
    private Long id;

    private String parentData;

    public SingleTableParent(final String parentData) {
        this.parentData = parentData;
    }
}

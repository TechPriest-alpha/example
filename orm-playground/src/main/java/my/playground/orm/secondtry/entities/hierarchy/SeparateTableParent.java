package my.playground.orm.secondtry.entities.hierarchy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("parent")
public class SeparateTableParent {
    @Id
    @GeneratedValue
    private Long id;

    private String parentData;

    public SeparateTableParent(final String parentData) {
        this.parentData = parentData;
    }
}

package my.playground.orm.secondtry.entities.hierarchy;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@DiscriminatorValue("inheritor")
public class JoinTableInheritor extends JoinTableParent {

    private String inheritorValue;

    public JoinTableInheritor(final String parentData, final String inheritorValue) {
        super(parentData);
        this.inheritorValue = inheritorValue;
    }

    public JoinTableInheritor(final String inheritorValue) {
        this.inheritorValue = inheritorValue;
    }
}

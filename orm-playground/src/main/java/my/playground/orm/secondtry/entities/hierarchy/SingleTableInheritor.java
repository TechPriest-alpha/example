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
public class SingleTableInheritor extends SingleTableParent {

    private String inheritorValue;

    public SingleTableInheritor(final String parentData, final String inheritorValue) {
        super(parentData);
        this.inheritorValue = inheritorValue;
    }

    public SingleTableInheritor(final String inheritorValue) {
        this.inheritorValue = inheritorValue;
    }
}

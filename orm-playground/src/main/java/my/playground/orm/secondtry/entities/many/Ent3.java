package my.playground.orm.secondtry.entities.many;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ent3 {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany(mappedBy = "ent3", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Ent1> ents1;

    @OneToMany(mappedBy = "ent3", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Ent2> ents2;

    @Override
    public String toString() {
        return "Ent3{" +
            "id=" + id +
            ", ents1=" + (ents1 == null ? null : ents1.stream().map(ent -> "Ent1(" + ent.getId() + ")").toList()) +
            ", ents2=" + (ents2 == null ? null : ents2.stream().map(ent -> "Ent2(" + ent.getId() + ")").toList()) +
            '}';
    }
}

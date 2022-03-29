package my.playground.orm.firsttry.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;
import my.playground.orm.firsttry.entities.sub.UserId;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @EmbeddedId
    private UserId id;

    private String name;

    public UserEntity(final UserId id) {
        this.id = id;
    }

    public UserEntity(final String name) {
        this.id = new UserId(UUID.randomUUID());
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        final UserEntity that = (UserEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

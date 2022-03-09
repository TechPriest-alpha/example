package my.playground.orm.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.playground.orm.entities.sub.UserId;

import java.util.UUID;

@Entity
@Data
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
}

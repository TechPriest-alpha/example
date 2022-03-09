package my.playground.orm.entities.sub;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
public class UserId implements Serializable {
    private UUID id;

    public UserId(final UUID randomUUID) {
        this.id = randomUUID;
    }
}

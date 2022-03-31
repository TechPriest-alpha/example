package my.playground.orm.secondtry.entities.map;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "mapped_message")
public class MappedMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column
    String content;
    @OneToOne(cascade = CascadeType.ALL)
    MappedEmail email;

    public MappedMessage(final String broken) {
        this.content = broken;
    }

    @Override
    public String toString() {
        return "MappedMessage{" +
            "id=" + id +
            ", content='" + content + '\'' +
            ", email=" + (email == null ? null : email.getId() + ":" + email.getSubject() + ":" + (email.getMessage() == null ? null : email.getMessage().content)) +
            '}';
    }
}

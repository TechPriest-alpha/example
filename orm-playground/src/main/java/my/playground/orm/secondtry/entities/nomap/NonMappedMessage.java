package my.playground.orm.secondtry.entities.nomap;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "non_mapped_message")
public class NonMappedMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column
    String content;
    @OneToOne
    NonMappedEmail email;

    public NonMappedMessage(final String broken) {
        this.content = broken;
    }

    @Override
    public String toString() {
        return "NonMappedMessage{" +
            "id=" + id +
            ", content='" + content + '\'' +
            ", email=" + (email == null ? null : email.getId() + ":" + email.getSubject() + ":" + (email.getMessage() == null ? null : email.getMessage().content)) +
            '}';
    }
}

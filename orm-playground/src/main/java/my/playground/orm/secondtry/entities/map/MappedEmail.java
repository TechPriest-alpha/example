package my.playground.orm.secondtry.entities.map;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "mapped_email")
public class MappedEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    private String subject;
    @OneToOne(mappedBy = "email", cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true)
    private MappedMessage message;

    public MappedEmail(final String broken) {
        this.subject = broken;
    }

    @Override
    public String toString() {
        return "MappedEmail{" +
            "id=" + id +
            ", subject='" + subject + '\'' +
            ", message=" + (message == null ? null : message.getId() + ":" + message.getContent() + ":" + (message.getEmail() == null ? null : message.getEmail().getSubject())) +
            '}';
    }
}

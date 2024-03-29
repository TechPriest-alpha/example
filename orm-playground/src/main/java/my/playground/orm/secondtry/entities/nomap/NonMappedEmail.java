package my.playground.orm.secondtry.entities.nomap;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "non_mapped_email")
public class NonMappedEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String subject;
    @OneToOne //(mappedBy = "email")
    private NonMappedMessage message;

    public NonMappedEmail(final String broken) {
        this.subject = broken;
    }

    @Override
    public String toString() {
        return "NonMappedEmail{" +
            "id=" + id +
            ", subject='" + subject + '\'' +
            ", message=" + (message == null ? null : message.getId() + ":" + message.getContent() + ":" + (message.getEmail() == null ? null : message.getEmail().subject)) +
            '}';
    }
}

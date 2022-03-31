package my.playground.orm.secondtry.entities.lazyness;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
//@Setter
@NoArgsConstructor
@Table(name = "mapped_email_eager")
@SecondaryTable(name = "mapped_email_subject")
public class MappedEmailEager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(table = "mapped_email_subject")
    private String subject;
    @OneToOne(mappedBy = "email", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @LazyToOne(LazyToOneOption.PROXY)
    private MappedMessageEager message;

    public MappedEmailEager(final String broken) {
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

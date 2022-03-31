package my.playground.orm.secondtry.entities.lazyness;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Getter
//@Setter
@NoArgsConstructor
@Table(name = "mapped_email")
public class MappedEmailLazy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String subject;
    @OneToOne(mappedBy = "email", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.PROXY)
    private MappedMessageLazy message;

    public MappedEmailLazy(final String broken) {
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

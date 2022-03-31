package my.playground.orm.secondtry.entities.lazyness;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
//@Setter
@NoArgsConstructor
@Table(name = "mapped_message")
public class MappedMessageLazy {
    @Id
    @UuidGenerator
    private UUID id;
    @Column
    private String content;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.PROXY)
    @Setter
    private MappedEmailLazy email;

    public MappedMessageLazy(final String broken) {
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

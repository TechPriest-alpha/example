package my.playground.orm.secondtry.entities.lazyness;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mapped_message")
public class MappedMessageEager {
    @Id
    @UuidGenerator
    private UUID id;
    @Column
    private String content;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @LazyToOne(LazyToOneOption.PROXY)
    @Setter
    private MappedEmailEager email;

    public MappedMessageEager(final String broken) {
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

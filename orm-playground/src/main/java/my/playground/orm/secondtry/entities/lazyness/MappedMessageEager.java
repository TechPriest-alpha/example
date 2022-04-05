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
@Table(name = "mapped_message_eager")
@SecondaryTable(name = "mapped_message_content")
public class MappedMessageEager {
    @Id
    @UuidGenerator
    private UUID id;
    @Column(table = "mapped_message_content")
    @Basic(fetch = FetchType.EAGER)
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

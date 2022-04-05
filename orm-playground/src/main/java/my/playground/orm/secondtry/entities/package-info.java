@NamedQueries(
    {
        @NamedQuery(
            name = "findMessageByContent",
            query = "from MappedMessageEager m where m.content like '%' || :input || '%' order by m.id",
//            fetchSize = 10,
            readOnly = true
        ),
        @NamedQuery(
            name = "findMappedMessageByContent",
            query = "from MappedMessage m where m.content like '%' || :input || '%' order by m.id",
//            fetchSize = 10,
            readOnly = true
        )
    }
)
package my.playground.orm.secondtry.entities;


import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
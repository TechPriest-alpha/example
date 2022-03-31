@NamedQueries(
    {
        @NamedQuery(
            name = "findMessageByContent",
            query = "from MappedMessageEager m where m.content like '%' || :input || '%'",
            fetchSize = 10,
            readOnly = true
        )
    }
)
package my.playground.orm.secondtry.entities;


import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
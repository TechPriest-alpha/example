package my.playground.orm.thirdtry;

import lombok.extern.slf4j.Slf4j;
import my.playground.orm.thirdtry.tables.records.AuthorRecord;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static my.playground.orm.thirdtry.Tables.*;

@Slf4j
public class JooqTest {
    final String url = "jdbc:sqlite:b:/programs/sqlite/orm";
    final String userName = "";
    final String password = "";

    @Test
    void simple() throws SQLException {

        try (final Connection conn = DriverManager.getConnection(url, userName, password)) {
            // ...
            final DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
            final AuthorRecord r10 = create.newRecord(AUTHOR);
            r10.setId(10);
            r10.setFirstName("RecName");
            r10.setLastName("RecLastName");
            r10.refresh();
            r10.store();

            final AuthorRecord r1 = create.newRecord(AUTHOR);
            r1.setId(1);
            r1.setFirstName("First");
            r1.setLastName("Name");
            r1.refresh();
            r1.store();
            final AuthorRecord r2 = create.newRecord(AUTHOR);
            r2.setId(2);
            r2.setFirstName("Second");
            r2.setLastName("Name");
            r2.refresh();
            r2.store();
            final AuthorRecord r3 = create.newRecord(AUTHOR);
            r3.setId(3);
            r3.setFirstName("Third");
            r3.setLastName("Name");
            r3.refresh();
            r3.store();
            final Result<Record> result = create.select().from(AUTHOR).fetch();
            for (final Record r : result) {
                final Integer id = r.getValue(AUTHOR.ID);
                final String firstName = r.getValue(AUTHOR.FIRST_NAME);
                final String lastName = r.getValue(AUTHOR.LAST_NAME);

                log.info("ID: {} first name: {} last name: {}", id, firstName, lastName);
            }
        } catch (final Exception ex) {
            log.info("Error", ex);
        }

        try (final Connection conn = DriverManager.getConnection(url, userName, password)) {
            final DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
            final Result<Record> result = create.select().from(AUTHOR).fetch();
            for (final Record r : result) {
                final Integer id = r.getValue(AUTHOR.ID);
                final String firstName = r.getValue(AUTHOR.FIRST_NAME);
                final String lastName = r.getValue(AUTHOR.LAST_NAME);

                log.info("ID: {} first name: {} last name: {}", id, firstName, lastName);
            }
        }
    }

    @Test
    void laziness() throws SQLException {
        try (final Connection conn = DriverManager.getConnection(url, userName, password)) {
            final DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
            final SelectQuery<Record> query = create.selectQuery();
            query.addFrom(MAPPED_MESSAGE_EAGER);
            query.addJoin(MAPPED_MESSAGE_CONTENT, MAPPED_MESSAGE_CONTENT.ID.eq(MAPPED_MESSAGE_EAGER.ID));
            query.addConditions(MAPPED_MESSAGE_CONTENT.CONTENT.like("%" + "message with data" + "%"));
            query.addOrderBy(MAPPED_MESSAGE_EAGER.ID.desc());
            final Result<Record> result = query.fetch();
            for (final Record r : result) {
                log.info("Result: {}", r);
            }
        }
    }
}

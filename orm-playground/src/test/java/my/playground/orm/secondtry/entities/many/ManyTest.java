package my.playground.orm.secondtry.entities.many;

import lombok.extern.slf4j.Slf4j;
import my.playground.orm.secondtry.services.SessionUtil;
import org.junit.jupiter.api.Test;

@Slf4j
public class ManyTest {

    @Test
    void name() {
        int id = 0;
        try (final var s = SessionUtil.getSession()) {
            final var tx = s.beginTransaction();
            var ent3_1 = new Ent3();
            var ent3_2 = new Ent3();

            var ent1_1 = new Ent1();
            var ent1_2 = new Ent1();
            var ent1_3 = new Ent1();
            var ent1_4 = new Ent1();
            var ent2_1 = new Ent2();
            var ent2_2 = new Ent2();
            var ent2_3 = new Ent2();
            var ent2_4 = new Ent2();
            ent1_1.setEnt3(ent3_1);
            ent1_2.setEnt3(ent3_1);
            ent1_3.setEnt3(ent3_1);
            ent1_4.setEnt3(ent3_2);
            ent2_1.setEnt3(ent3_2);
            ent2_2.setEnt3(ent3_1);
            ent2_3.setEnt3(ent3_1);
            ent2_4.setEnt3(ent3_1);


            s.persist(ent1_1);
            s.persist(ent1_2);
            s.persist(ent1_3);
            s.persist(ent1_4);
            s.persist(ent2_1);
            s.persist(ent2_2);
            s.persist(ent2_3);
            s.persist(ent2_4);
//            ent3_1.setEnts1(List.of(ent1_1, ent1_2, ent1_3, ent1_4));
//            s.persist(ent3_1);
            tx.commit();
            id = ent3_1.getId();
            log.info("Ent: {}", ent3_1);
        }

        try (final var s = SessionUtil.getSession()) {
            var ent = s.find(Ent3.class, id);
            log.info("Ent3: {}", ent);
            log.info("Ent3: {}", ent.getEnts1());
            var ent1 = s.find(Ent1.class, 1);
            log.info("Ent1: {}", ent1);
            var ent2 = s.find(Ent2.class, 1);
            log.info("Ent2: {}", ent2);
        }
        try (final var s = SessionUtil.getSession()) {
            var q = s.createQuery("from Ent3 ", Ent3.class);
            var result = q.list();
            log.info("Result: {}", result);
        }
    }
}

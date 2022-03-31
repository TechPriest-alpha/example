package my.playground.orm.secondtry.entities.hierarchy;

import lombok.extern.slf4j.Slf4j;
import my.playground.orm.secondtry.services.SessionUtil;
import org.junit.jupiter.api.Test;

@Slf4j
public class HierarchyTest {
    @Test
    void singleTableTest() {
        Long parentId, inheritor1Id, inheritor2Id;
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            final var parent = new SingleTableParent("I am single table Parent!");
            final var inheritor1 = new SingleTableInheritor("I am single table Inheritor with no parent data");
            final var inheritor2 = new SingleTableInheritor("Some parent Data", " am single table Inheritor!");
            session.persist(parent);
            session.persist(inheritor1);
            session.persist(inheritor2);
            tx.commit();
            parentId = parent.getId();
            inheritor1Id = inheritor1.getId();
            inheritor2Id = inheritor2.getId();
            log.info("\n1 Parent: {}\nInheritor1: {}\nInheritor2: {}", parent, inheritor1, inheritor2);
        }

        try (final var session = SessionUtil.getSession()) {
            final var parent = session.get(SingleTableParent.class, parentId);
            final var inheritor1 = session.get(SingleTableInheritor.class, inheritor1Id);
            final var inheritor2 = session.get(SingleTableInheritor.class, inheritor2Id);

            log.info("\n2 Parent: {}\nInheritor1: {}\nInheritor2: {}", parent, inheritor1, inheritor2);
        }

        try (final var session = SessionUtil.getSession()) {
            final SingleTableParent parent = session.get(SingleTableParent.class, inheritor1Id);
            log.warn("3 Unexpected success: {}, {}", parent, parent.getClass().getSimpleName());
        } catch (final Exception ex) {
            log.warn("3 Expected exception", ex);
        }

        try (final var session = SessionUtil.getSession()) {
            final SingleTableInheritor inheritor1 = session.get(SingleTableInheritor.class, parentId);

            log.warn("4 Unexpected success: {}", inheritor1);
        } catch (final Exception ex) {
            log.warn("4 Expected exception", ex);
        }
    }

    @Test
    void joinTableTest() {
        Long parentId, inheritor1Id, inheritor2Id;
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            final var parent = new JoinTableParent("I am single table Parent!");
            final var inheritor1 = new JoinTableInheritor("I am single table Inheritor with no parent data");
            final var inheritor2 = new JoinTableInheritor("Some parent Data", " am single table Inheritor!");
            session.persist(parent);
            session.persist(inheritor1);
            session.persist(inheritor2);
            tx.commit();
            parentId = parent.getId();
            inheritor1Id = inheritor1.getId();
            inheritor2Id = inheritor2.getId();
            log.info("\n1 Parent: {}\nInheritor1: {}\nInheritor2: {}", parent, inheritor1, inheritor2);
        }

        try (final var session = SessionUtil.getSession()) {
            final var parent = session.get(JoinTableParent.class, parentId);
            final var inheritor1 = session.get(JoinTableInheritor.class, inheritor1Id);
            final var inheritor2 = session.get(JoinTableInheritor.class, inheritor2Id);

            log.info("\n2 Parent: {}\nInheritor1: {}\nInheritor2: {}", parent, inheritor1, inheritor2);
        }

        try (final var session = SessionUtil.getSession()) {
            final JoinTableParent parent = session.get(JoinTableParent.class, inheritor1Id);
            log.warn("3 Unexpected success: {}", parent);
        } catch (final Exception ex) {
            log.warn("3 Expected exception", ex);
        }

        try (final var session = SessionUtil.getSession()) {
            final JoinTableInheritor inheritor1 = session.get(JoinTableInheritor.class, parentId);

            log.warn("4 Unexpected success: {}", inheritor1);
        } catch (final Exception ex) {
            log.warn("4 Expected exception", ex);
        }
    }

    @Test
    void separateTableTest() {
        Long parentId, inheritor1Id, inheritor2Id;
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            final var parent = new SeparateTableParent("I am single table Parent!");
            final var inheritor1 = new SeparateTableInheritor("I am single table Inheritor with no parent data");
            final var inheritor2 = new SeparateTableInheritor("Some parent Data", "I am single table Inheritor!");
            session.persist(parent);
            session.persist(inheritor1);
            session.persist(inheritor2);
            tx.commit();
            parentId = parent.getId();
            inheritor1Id = inheritor1.getId();
            inheritor2Id = inheritor2.getId();
            log.info("\n1 Parent: {}\nInheritor1: {}\nInheritor2: {}", parent, inheritor1, inheritor2);
        }

        try (final var session = SessionUtil.getSession()) {
            final var parent = session.get(SeparateTableParent.class, parentId);
            final var inheritor1 = session.get(SeparateTableInheritor.class, inheritor1Id);
            final var inheritor2 = session.get(SeparateTableInheritor.class, inheritor2Id);

            log.info("\n2 Parent: {}\nInheritor1: {}\nInheritor2: {}", parent, inheritor1, inheritor2);
        }

        try (final var session = SessionUtil.getSession()) {
            final var parent = session.get(SeparateTableParent.class, inheritor1Id);
            log.warn("3 Unexpected success: {}", parent);
        } catch (final Exception ex) {
            log.warn("3 Expected exception", ex);
        }

        try (final var session = SessionUtil.getSession()) {
            final var inheritor1 = session.get(SeparateTableInheritor.class, parentId);

            log.warn("4 Unexpected success: {}", inheritor1);
        } catch (final Exception ex) {
            log.warn("4 Expected exception", ex);
        }
    }
}

package my.playground.orm.secondtry.services;

import my.playground.orm.secondtry.entities.Person;
import my.playground.orm.secondtry.entities.Ranking;
import my.playground.orm.secondtry.entities.Skill;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RankingService implements IRankingService {
    @Override
    public double getRankingFor(final String subject, final String skill) {
        try (Session session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            final var query = session.createQuery("from Ranking r where r.subject.name=:name and r.skill.name=:skill", Ranking.class);
            query.setParameter("name", subject);
            query.setParameter("skill", skill);

            final double result = query.list().stream().collect(Collectors.summarizingInt(Ranking::getRank)).getAverage();
            tx.commit();
            return result;
        }
    }

    @Override
    public void addRanking(final String subjectName, final String observerName, final String skillName, final int rank) {
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            addRank(session, subjectName, observerName, skillName, rank);
            tx.commit();
        }
    }

    @Override
    public void updateRanking(final String subject, final String observer, final String skill, final int ranking) {
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            final var rank = findRanking(session, subject, observer, skill);
            if (rank == null) {
                addRank(session, subject, observer, skill, ranking);
            } else {
                rank.setRank(ranking);
            }
            tx.commit();
        }
    }

    @Override
    public void removeRanking(final String subject, final String observer, final String skill) {
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            final var rank = findRanking(session, subject, observer, skill);
            if (rank != null) {
                session.remove(rank);
            }
            tx.commit();
        }
    }

    @Override
    public Map<String, Integer> findRankingsFor(final String somebody) {
        try (final var session = SessionUtil.getSession()) {
            return findRankingsFor(session, somebody);
        }
    }

    @Override
    public Person findBestPersonFor(final String skill) {
        try (final var session = SessionUtil.getSession()) {
            return findBestPersonFor(session, skill);
        }
    }

    private Person findBestPersonFor(final Session session, final String skill) {
        final var query = session.createQuery("select r.subject.id from Ranking r where r.skill.name=:skill group by r.subject.id order by avg(r.rank) desc", Long.class);
        query.setParameter("skill", skill);
        final var result = query.list();
        if (result.isEmpty()) {
            return null;
        } else {
            return session.get(Person.class, result.get(0));
        }
    }

    private Map<String, Integer> findRankingsFor(final Session session, final String somebody) {
        final Map<String, Integer> results = new HashMap<>();
        final var query = session.createQuery("from Ranking r where r.subject.name=:subject order by r.skill.name", Ranking.class);
        query.setParameter("subject", somebody);
        final var rankings = query.list();
        String lastSkillName = "";
        int sum = 0;
        int count = 0;
        for (final var r : rankings) {
            if (!lastSkillName.equals(r.getSkill().getName())) {
                sum = 0;
                count = 0;
                lastSkillName = r.getSkill().getName();
            }
            sum += r.getRank();
            count++;
            results.put(lastSkillName, sum / count);
        }
        return results;
    }

    private void addRank(final Session session, final String subjectName, final String observerName, final String skillName, final int rank) {
        final Person subject = savePerson(session, subjectName);
        final Person observer = savePerson(session, observerName);
        final Skill skill = saveSkill(session, skillName);
        final Ranking ranking = new Ranking();
        ranking.setSubject(subject);
        ranking.setObserver(observer);
        ranking.setSkill(skill);
        ranking.setRank(rank);

        session.persist(ranking);
    }


    public Ranking findRanking(final String subjectName, final String observerName, final String skillName) {
        try (final var session = SessionUtil.getSession()) {

            return findRanking(session, subjectName, observerName, skillName);
        }
    }

    private Ranking findRanking(final Session session, final String subjectName, final String observerName, final String skillName) {
        final var query = session.createQuery("from Ranking r where r.subject.name=:subjectName and r.observer.name=:observerName and r.skill.name=:skillName", Ranking.class);
        query.setParameter("subjectName", subjectName);
        query.setParameter("observerName", observerName);
        query.setParameter("skillName", skillName);
        return query.uniqueResult();
    }

    private Skill saveSkill(final Session session, final String name) {
        final var skill = findSkill(session, name);
        if (skill == null) {
            final var newSkill = new Skill();
            newSkill.setName(name);
            session.persist(newSkill);
            return newSkill;
        } else {
            return skill;
        }
    }

    private Skill findSkill(final Session session, final String name) {
        final var query = session.createQuery("from Skill s where s.name=:name", Skill.class);
        query.setParameter("name", name);
        return query.uniqueResult();
    }

    private Person savePerson(final Session session, final String name) {
        final var person = findPerson(session, name);
        if (person == null) {
            final var newPerson = new Person();
            newPerson.setName(name);
            session.persist(newPerson);
            return newPerson;
        } else {
            return person;
        }
    }

    private Person findPerson(final Session session, final String name) {
        final var query = session.createQuery("from Person p where p.name=:name", Person.class);
        query.setParameter("name", name);
        return query.uniqueResult();
    }
}

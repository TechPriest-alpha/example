package my.playground.orm.secondtry.services;

import my.playground.orm.secondtry.entities.Person;

import java.util.Map;

public interface IRankingService {
    double getRankingFor(String subject, String skill);
    void addRanking(String subject, String observer, String skill, int ranking);

    void updateRanking(String subject, String observer, String skill, int ranking);

    void removeRanking(String subject, String observer, String skill);

    Map<String, Integer> findRankingsFor(String somebody);

    Person findBestPersonFor(String skill);
}

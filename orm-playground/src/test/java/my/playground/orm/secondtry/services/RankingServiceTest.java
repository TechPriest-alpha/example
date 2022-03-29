package my.playground.orm.secondtry.services;


import lombok.extern.slf4j.Slf4j;
import my.playground.orm.secondtry.entities.Person;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class RankingServiceTest extends RankingService {
    RankingService rankingService = new RankingService();

    @Test
    public void removeRanking() {
        rankingService.addRanking("R1", "R2", "RS1", 8);
        assertEquals(rankingService.getRankingFor("R1", "RS1"), 8);
        rankingService.removeRanking("R1", "R2", "RS1");
        assertEquals(rankingService.getRankingFor("R1", "RS1"), 0);
    }

    @Test
    public void removeNonexistentRanking() {
        rankingService.removeRanking("R3", "R4", "RS2");
    }

    @Test
    public void changeRanking() {
        rankingService.addRanking("Gene Showrama", "Scottball Most", "Ceylon", 6);
        assertEquals(rankingService.getRankingFor("Gene Showrama", "Ceylon"), 6);
        rankingService.updateRanking("Gene Showrama", "Scottball Most", "Ceylon", 7);
        assertEquals(rankingService.getRankingFor("Gene Showrama", "Ceylon"), 7);
    }

    @Test
    public void validateRankingAverage() {
        rankingService.addRanking("A", "B", "C", 4);
        rankingService.addRanking("A", "B", "C", 5);
        rankingService.addRanking("A", "B", "C", 6);
        assertEquals(rankingService.getRankingFor("A", "C"), 5);
        rankingService.addRanking("A", "B", "C", 7);
        rankingService.addRanking("A", "B", "C", 8);
        assertEquals(rankingService.getRankingFor("A", "C"), 6);
    }

    @Test
    public void findNoRankings() {
        assertEquals(rankingService.getRankingFor("Nobody", "Java"), 0);
        assertEquals(rankingService.getRankingFor("Nobody", "Python"), 0);
        final Map<String, Integer> rankings = rankingService.findRankingsFor("Nobody");
        assertEquals(rankings.size(), 0);

    }

    @Test
    public void findAllRankings() {
        assertEquals(rankingService.getRankingFor("Somebody", "Java"), 0);
        assertEquals(rankingService.getRankingFor("Somebody", "Python"), 0);
        rankingService.addRanking("Somebody", "Nobody", "Java", 9);
        rankingService.addRanking("Somebody", "Nobody", "Java", 7);
        rankingService.addRanking("Somebody", "Nobody", "Python", 7);
        rankingService.addRanking("Somebody", "Nobody", "Python", 5);
        final Map<String, Integer> rankings = rankingService.findRankingsFor("Somebody");
        assertEquals(rankings.size(), 2);

        assertNotNull(rankings.get("Java"));

        assertEquals(rankings.get("Java"), 8);

        assertNotNull(rankings.get("Python"));

        assertEquals(rankings.get("Python"), 6);
    }

    @Test
    public void findBestForNonexistentSkill() {
        Person p = rankingService.findBestPersonFor("no skill");
        assertNull(p);
    }

    @Test
    public void findBestForSkill() {
        rankingService.addRanking("S1", "O1", "Sk1", 6);
        rankingService.addRanking("S1", "O2", "Sk1", 8);
        rankingService.addRanking("S2", "O1", "Sk1", 5);
        rankingService.addRanking("S2", "O2", "Sk1", 7);
        rankingService.addRanking("S3", "O1", "Sk1", 7);
        rankingService.addRanking("S3", "O2", "Sk1", 9);
        // data that should not factor in!
        rankingService.addRanking("S3", "O1", "Sk2", 2);
        Person p = rankingService.findBestPersonFor("Sk1");
        assertEquals("S3", p.getName());
    }

}
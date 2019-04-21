package io.example.rps.game.model;

import io.example.rps.game.strategies.FixedRockStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GamingSessionTest {
    private GamingSession gamingSession;

    @BeforeEach
    void setUp() {
        this.gamingSession = new GamingSession(new User(""), new FixedRockStrategy());
    }

    @Test
    void verifyStats() {
        gamingSession.update(ValidMove.PAPER, ValidMove.ROCK, RoundResult.WIN);
        gamingSession.update(ValidMove.PAPER, ValidMove.SCISSORS, RoundResult.LOOSE);
        gamingSession.update(ValidMove.ROCK, ValidMove.ROCK, RoundResult.DRAW);
        gamingSession.update(ValidMove.PAPER, ValidMove.ROCK, RoundResult.WIN);
        final var userStats = gamingSession.getStats();
        assertEquals(userStats.getTotalRounds(), 4);
        assertEquals(userStats.getUserWins(), 2);
        assertEquals(userStats.getUserLosses(), 1);
    }
}
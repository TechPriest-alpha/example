package io.example.rps.game.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoundResultTest {

    @Test
    @DisplayName("Rock wins Scissors")
    void rockWinsScissors() {
        assertEquals(RoundResult.deriveRoundResult(ValidMove.ROCK, ValidMove.SCISSORS), RoundResult.WIN);
    }

    @Test
    @DisplayName("Scissors win Paper")
    void scissorsWinPaper() {
        assertEquals(RoundResult.deriveRoundResult(ValidMove.SCISSORS, ValidMove.PAPER), RoundResult.WIN);
    }

    @Test
    @DisplayName("Paper wins Rock")
    void paperWinsRock() {
        assertEquals(RoundResult.deriveRoundResult(ValidMove.PAPER, ValidMove.ROCK), RoundResult.WIN);
    }

    @Test
    @DisplayName("Rock loses to Paper")
    void rockLosesToRock() {
        assertEquals(RoundResult.deriveRoundResult(ValidMove.ROCK, ValidMove.PAPER), RoundResult.LOOSE);
    }

    @Test
    @DisplayName("Paper loses to Scissors")
    void paperLosesToScissors() {
        assertEquals(RoundResult.deriveRoundResult(ValidMove.PAPER, ValidMove.SCISSORS), RoundResult.LOOSE);
    }

    @Test
    @DisplayName("Scissors lose to Rock")
    void scissorsLoseToRock() {
        assertEquals(RoundResult.deriveRoundResult(ValidMove.SCISSORS, ValidMove.ROCK), RoundResult.LOOSE);
    }

    @ParameterizedTest
    @MethodSource("validMoves")
    @DisplayName("Equal moves result in Draw")
    void checkDraws(final ValidMove validMove) {
        assertEquals(RoundResult.deriveRoundResult(validMove, validMove), RoundResult.DRAW);
    }

    @ParameterizedTest
    @MethodSource("validMoves")
    @DisplayName("Every move has its winning counterpart")
    void winningMoveAlwaysFound(final ValidMove validMove) {
        assertEquals(RoundResult.deriveRoundResult(RoundResult.getWinningMoveFor(validMove), validMove), RoundResult.WIN);
    }

    private static ValidMove[] validMoves() {
        return ValidMove.values();
    }
}
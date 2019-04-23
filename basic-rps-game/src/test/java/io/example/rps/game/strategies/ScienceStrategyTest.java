package io.example.rps.game.strategies;

import io.example.rps.game.model.RoundResult;
import io.example.rps.game.model.ValidMove;
import io.example.rps.game.strategies.custom.ScienceStrategy;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScienceStrategyTest {
    private static final int REPEATS = 10;
    private ScienceStrategy strategy;

    @RepeatedTest(REPEATS)
    void strategyAdjustmentIfWin() {
        strategy = new ScienceStrategy();

        strategy.nextMove(ValidMove.ROCK);
        strategy.adjustByResult(RoundResult.WIN);
        assertEquals(strategy.nextMove(ValidMove.ROCK), ValidMove.PAPER);
    }

    @RepeatedTest(REPEATS)
    void strategyAdjustmentIfLoose() {
        strategy = new ScienceStrategy();

        final var firstMove = strategy.nextMove(ValidMove.ROCK);
        strategy.adjustByResult(RoundResult.LOOSE);
        assertEquals(strategy.nextMove(ValidMove.ROCK), RoundResult.getWinningMoveFor(firstMove));
    }

    @RepeatedTest(REPEATS)
    void strategyAdjustmentIfDraw() {
        strategy = new ScienceStrategy();

        final var firstMove = strategy.nextMove(ValidMove.ROCK);
        strategy.adjustByResult(RoundResult.DRAW);
        assertEquals(strategy.nextMove(ValidMove.ROCK), firstMove);
    }
}
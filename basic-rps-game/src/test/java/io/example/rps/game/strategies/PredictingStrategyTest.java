package io.example.rps.game.strategies;

import io.example.rps.game.model.ValidMove;
import io.example.rps.game.strategies.stats.PredictingStrategy;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PredictingStrategyTest {

    private PredictingStrategy strategy;

    @RepeatedTest(10)
    void testSingleMove() {
        strategy = new PredictingStrategy();
        strategy.nextMove(ValidMove.ROCK);
        assertEquals(strategy.nextMove(ValidMove.ROCK), ValidMove.PAPER);
    }

    @RepeatedTest(100)
    void testMultiMove() {
        strategy = new PredictingStrategy();
        Arrays.stream(ValidMove.values()).forEach(move -> strategy.nextMove(move));
        final var wins = IntStream.range(0, 10000).mapToObj(i -> strategy.nextMove(ValidMove.ROCK)).filter(validMove -> validMove == ValidMove.PAPER).count();
        assertTrue(wins > 5000);

        final var wins2 = IntStream.range(0, 10).mapToObj(i -> strategy.nextMove(ValidMove.SCISSORS)).filter(validMove -> validMove == ValidMove.ROCK).count();
        assertTrue(wins2 < 10 && wins2 > 2);
    }
}
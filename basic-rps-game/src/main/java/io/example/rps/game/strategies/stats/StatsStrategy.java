package io.example.rps.game.strategies.stats;

import io.example.rps.game.model.ValidMove;
import io.example.rps.game.strategies.GameStrategy;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * For weighted number choice very basic approach is used.
 *
 * @see <a href="https://stackoverflow.com/questions/1761626/weighted-random-numbers">article</a>
 */
public class StatsStrategy extends BaseStatsStrategy {
    private final Random RANDOM = ThreadLocalRandom.current();
    private final Map<ValidMove, Integer> stats = new EnumMap<>(ValidMove.class);

    public ValidMove nextMove(final ValidMove userMove) {
        stats.compute(userMove, (k, count) -> count == null ? 1 : count + 1);
        return getValidMove(stats);
    }



    @Override
    public String name() {
        return "Statistical Bot";
    }

    @Override
    public GameStrategy get() {
        return new StatsStrategy();
    }
}

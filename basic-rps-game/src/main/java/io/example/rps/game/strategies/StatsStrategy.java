package io.example.rps.game.strategies;

import io.example.rps.game.errors.GeneralInternalError;
import io.example.rps.game.model.RoundResult;
import io.example.rps.game.model.ValidMove;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * For weighted number choice very basic approach is used.
 *
 * @see <a href="https://stackoverflow.com/questions/1761626/weighted-random-numbers">article</a>
 */
public class StatsStrategy extends GameStrategy {
    private final Random RANDOM = ThreadLocalRandom.current();
    private final Map<ValidMove, Integer> stats = new EnumMap<>(ValidMove.class);

    public ValidMove nextMove(final ValidMove userMove) {
        stats.compute(userMove, (k, count) -> count == null ? 1 : count + 1);
        final var sum = stats.values().stream().mapToInt(v -> v).sum();
        var rnd = RANDOM.nextInt(sum);
        for (final var entry : stats.entrySet()) {
            if (rnd < entry.getValue()) {
                return RoundResult.getWinningMoveFor(entry.getKey()); //user used this move
            }
            rnd -= entry.getValue();
        }
        throw new GeneralInternalError("Logic is broken");
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

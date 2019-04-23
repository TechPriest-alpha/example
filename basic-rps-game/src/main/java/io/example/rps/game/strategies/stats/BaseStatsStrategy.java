package io.example.rps.game.strategies.stats;

import io.example.rps.game.errors.GeneralInternalError;
import io.example.rps.game.model.RoundResult;
import io.example.rps.game.model.ValidMove;
import io.example.rps.game.strategies.GameStrategy;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

abstract class BaseStatsStrategy implements GameStrategy {
    final Random RANDOM = ThreadLocalRandom.current();

    ValidMove getValidMove(final Map<ValidMove, Integer> stats) {
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
}

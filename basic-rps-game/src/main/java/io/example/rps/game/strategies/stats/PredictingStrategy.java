package io.example.rps.game.strategies.stats;

import io.example.rps.game.model.ValidMove;
import io.example.rps.game.strategies.GameStrategy;
import lombok.Data;
import lombok.val;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Variation of stats strategy, that tries to predict next user move basing on known history.
 */
public class PredictingStrategy extends BaseStatsStrategy {
    private final Map<ValidMove, Map<ValidMove, Integer>> stats = new EnumMap<>(ValidMove.class);
    private ValidMove prevUserMove = null;

    public ValidMove nextMove(final ValidMove userMove) {
        if (prevUserMove != null) {
            stats.compute(prevUserMove, (k1, freq) -> {
                if (freq == null) {
                    freq = new EnumMap<>(ValidMove.class);
                }
                freq.compute(userMove, (k2, count) -> count == null ? 1 : count + 1);
                return freq;
            });
        }
        final var subStats = stats.getOrDefault(prevUserMove, Collections.emptyMap());
        prevUserMove = userMove;

        if (subStats.isEmpty()) {
            final val nextMove = RANDOM.nextInt(AVAILABLE_MOVE_LENGTH);
            return ValidMove.values()[nextMove];
        } else {
            return getValidMove(subStats);
        }
    }

    @Override
    public String name() {
        return "Predicting Bot";
    }

    @Override
    public GameStrategy get() {
        return new PredictingStrategy();
    }

    @Data
    private static class MoveFrequency {
        ValidMove nextUserMove;
        Integer count;
    }
}

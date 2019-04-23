package io.example.rps.game.strategies;

import io.example.rps.game.model.ValidMove;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class PredictingStrategy extends GameStrategy {
    private final Random RANDOM = ThreadLocalRandom.current();
    private final Map<ValidMove, Long> stats = new EnumMap<>(ValidMove.class);

    public ValidMove nextMove(final ValidMove userMove) {
        return null;
    }

    @Override
    public String name() {
        return "Predicting Bot";
    }

    @Override
    public GameStrategy get() {
        return new PredictingStrategy();
    }
}

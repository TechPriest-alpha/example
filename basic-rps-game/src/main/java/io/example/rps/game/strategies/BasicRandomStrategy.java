package io.example.rps.game.strategies;

import io.example.rps.game.model.ValidMove;
import lombok.val;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BasicRandomStrategy implements GameStrategy {
    private final Random RANDOM = ThreadLocalRandom.current();
    private final int AVAILABLE_MOVE_LENGTH = ValidMove.values().length;

    public ValidMove nextMove(final ValidMove userMove) {
        final val nextMove = RANDOM.nextInt(AVAILABLE_MOVE_LENGTH);
        return ValidMove.values()[nextMove];
    }

    @Override
    public String name() {
        return "Basic Random Bot";
    }
}

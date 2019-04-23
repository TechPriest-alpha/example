package io.example.rps.game.strategies.basic;

import io.example.rps.game.model.ValidMove;
import io.example.rps.game.strategies.GameStrategy;
import lombok.val;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BasicRandomStrategy implements GameStrategy {
    private final Random RANDOM = ThreadLocalRandom.current();

    public ValidMove nextMove(final ValidMove userMove) {
        final val nextMove = RANDOM.nextInt(AVAILABLE_MOVE_LENGTH);
        return ValidMove.values()[nextMove];
    }

    @Override
    public String name() {
        return "Basic Random Bot";
    }

    @Override
    public GameStrategy get() {
        return new BasicRandomStrategy();
    }
}

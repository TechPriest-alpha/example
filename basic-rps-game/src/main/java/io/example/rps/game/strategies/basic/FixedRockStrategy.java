package io.example.rps.game.strategies.basic;

import io.example.rps.game.model.ValidMove;
import io.example.rps.game.strategies.GameStrategy;

public class FixedRockStrategy implements GameStrategy {
    @Override
    public ValidMove nextMove(final ValidMove userMove) {
        return ValidMove.ROCK;
    }

    @Override
    public String name() {
        return "Single-move (ROCK) bot";
    }

    @Override
    public GameStrategy get() {
        return this;
    }
}

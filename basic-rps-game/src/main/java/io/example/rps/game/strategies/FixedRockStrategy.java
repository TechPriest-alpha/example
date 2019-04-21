package io.example.rps.game.strategies;

import io.example.rps.game.model.ValidMove;

public class FixedRockStrategy extends GameStrategy {
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

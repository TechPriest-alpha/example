package io.example.rps.game.strategies;

import io.example.rps.game.model.ValidMove;

import java.util.function.Supplier;

public abstract class GameStrategy implements Supplier<GameStrategy> {
    public abstract ValidMove nextMove(final ValidMove userMove);

    public abstract String name();
}

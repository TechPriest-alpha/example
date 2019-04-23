package io.example.rps.game.strategies;

import io.example.rps.game.model.RoundResult;
import io.example.rps.game.model.ValidMove;

import java.util.function.Supplier;

public abstract class GameStrategy implements Supplier<GameStrategy> {
    static final int AVAILABLE_MOVE_LENGTH = ValidMove.values().length;

    public abstract ValidMove nextMove(final ValidMove userMove);

    public abstract String name();

    public void adjustByResult(final RoundResult roundResult) {
        //noop
    }
}

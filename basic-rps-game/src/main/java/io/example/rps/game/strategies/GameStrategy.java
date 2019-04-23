package io.example.rps.game.strategies;

import io.example.rps.game.model.RoundResult;
import io.example.rps.game.model.ValidMove;

import java.util.function.Supplier;

public interface GameStrategy extends Supplier<GameStrategy> {
    int AVAILABLE_MOVE_LENGTH = ValidMove.values().length;

    ValidMove nextMove(final ValidMove userMove);

    String name();

    default void adjustByResult(final RoundResult roundResult) {
        //noop
    }
}

package io.example.rps.game.strategies.basic;

import io.example.rps.game.model.RoundResult;
import io.example.rps.game.model.ValidMove;
import io.example.rps.game.strategies.GameStrategy;

public class CheatingStrategy implements GameStrategy {

    public ValidMove nextMove(final ValidMove userMove) {
        return RoundResult.getWinningMoveFor(userMove);
    }

    @Override
    public String name() {
        return "Cheating Bot";
    }

    @Override
    public GameStrategy get() {
        return this;
    }
}

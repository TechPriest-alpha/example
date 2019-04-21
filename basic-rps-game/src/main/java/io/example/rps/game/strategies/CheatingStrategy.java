package io.example.rps.game.strategies;

import io.example.rps.game.model.RoundResult;
import io.example.rps.game.model.ValidMove;

public class CheatingStrategy implements GameStrategy {

    public ValidMove nextMove(final ValidMove userMove) {
        return RoundResult.getWinningMoveFor(userMove);
    }

    @Override
    public String name() {
        return "Cheating Bot";
    }
}

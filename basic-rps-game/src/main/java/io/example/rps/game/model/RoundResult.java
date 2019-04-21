package io.example.rps.game.model;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public enum RoundResult {
    WIN, LOOSE, DRAW;

    private static final Map<ValidMove, List<ValidMove>> WINNING_MOVES = new EnumMap<>(ValidMove.class);

    static {
        WINNING_MOVES.put(ValidMove.PAPER, Collections.singletonList(ValidMove.ROCK));
        WINNING_MOVES.put(ValidMove.ROCK, Collections.singletonList(ValidMove.SCISSORS));
        WINNING_MOVES.put(ValidMove.SCISSORS, Collections.singletonList(ValidMove.PAPER));
    }

    public static RoundResult deriveRoundResult(final ValidMove move1, final ValidMove move2) {
        if (move1.equals(move2)) {
            return DRAW;
        } else {
            return WINNING_MOVES.get(move1).contains(move2) ? WIN : LOOSE;
        }
    }

    public boolean isDraw() {
        return DRAW == this;
    }
}

package io.example.rps.game.model;

import io.example.rps.game.errors.GeneralInternalError;

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

    public static ValidMove getWinningMoveFor(final ValidMove validMove) {
        return WINNING_MOVES.entrySet().stream()
            .filter(e -> e.getValue().contains(validMove))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElseThrow(() -> new GeneralInternalError("No winning move for " + validMove.name()));
    }

    public boolean isDraw() {
        return DRAW == this;
    }

    public boolean isWin() {
        return WIN == this;
    }
}

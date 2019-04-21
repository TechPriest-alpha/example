package io.example.rps.game.strategies;

import io.example.rps.game.model.ValidMove;

public interface GameStrategy {
    ValidMove nextMove();
}

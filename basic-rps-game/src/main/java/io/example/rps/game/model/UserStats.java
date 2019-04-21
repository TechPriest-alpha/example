package io.example.rps.game.model;

import lombok.Value;

@Value
public class UserStats {
    private final int userWins;
    private final int userLosses;
    private final int totalRounds;
}

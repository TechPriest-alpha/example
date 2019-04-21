package io.example.rps.game.model;

import io.example.rps.game.strategies.GameStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.EnumMap;

@Value
public class GamingSession {
    private final User user;
    @Setter
    @NonFinal
    private GameStrategy strategy;
    @NonFinal
    @Getter
    private boolean active = true;
    @NonFinal
    @Getter
    private boolean tutorialMode = false;

    public GamingSession(final User user, final GameStrategy strategy) {
        this.user = user;
        this.strategy = strategy;
    }

    private final EnumMap<ValidMove, Integer> userMoves = new EnumMap<>(ValidMove.class);
    private final EnumMap<ValidMove, Integer> botMoves = new EnumMap<>(ValidMove.class);
    private final EnumMap<RoundResult, Integer> winLoss = new EnumMap<>(RoundResult.class);

    public void update(final ValidMove userMove, final ValidMove botMove, final RoundResult roundResult) {
        winLoss.compute(roundResult, (key, value) -> value == null ? 1 : value + 1);
        botMoves.compute(botMove, (key, value) -> value == null ? 1 : value + 1);
        userMoves.compute(userMove, (key, value) -> value == null ? 1 : value + 1);
    }

    public ValidMove nextMove(final ValidMove userMove) {
        return strategy.nextMove(userMove);
    }

    public UserStats getStats() {
        return new UserStats(
            winLoss.getOrDefault(RoundResult.WIN, 0),
            winLoss.getOrDefault(RoundResult.LOOSE, 0),
            userMoves.values().stream().mapToInt(v -> v).sum()
        );
    }

    public void closeSession() {
        active = false;
    }

    public void activateTutorialMode() {
        tutorialMode = true;
    }

    public void deactivateTutorialMode() {
        tutorialMode = false;
    }

}

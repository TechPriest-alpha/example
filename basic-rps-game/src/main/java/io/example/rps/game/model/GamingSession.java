package io.example.rps.game.model;

import io.example.rps.game.strategies.GameStrategy;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Value
public class GamingSession {
    private final User user;
    @Setter
    @NonFinal
    private GameStrategy strategy;
    private final AtomicBoolean isActive = new AtomicBoolean(true);
    private final AtomicBoolean tutorialMode = new AtomicBoolean(false);


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
        isActive.set(false);
    }

    public void activateTutorialMode(final GameStrategy tutorialStrategy) {
        tutorialMode.set(true);
        strategy = tutorialStrategy;
    }

    public void deactivateTutorialMode(final GameStrategy gameStrategy) {
        tutorialMode.set(false);
        strategy = gameStrategy;
    }

    public boolean isActive() {
        return isActive.get();
    }

    public boolean isTutorial() {
        return tutorialMode.get();
    }
}

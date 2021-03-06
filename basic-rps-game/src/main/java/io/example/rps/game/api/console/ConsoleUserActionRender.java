package io.example.rps.game.api.console;

import io.example.rps.game.api.UserActionHandler;
import io.example.rps.game.model.GamingSession;
import io.example.rps.game.model.RoundResult;
import io.example.rps.game.model.UserAction;
import io.example.rps.game.model.ValidMove;
import io.example.rps.game.strategies.StrategiesRegistry;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import java.util.EnumMap;

class ConsoleUserActionRender implements UserActionHandler {
    private final EnumMap<UserAction, UserActionHandler> userActionHandlers = new EnumMap<>(UserAction.class);
    private final GamingSession userSession;
    private final TextIO textIO;
    private final TextTerminal<?> terminal;

    ConsoleUserActionRender(final GamingSession gamingSession, final TextIO textIO) {
        this.userSession = gamingSession;
        this.textIO = textIO;
        terminal = textIO.getTextTerminal();
        userActionHandlers.put(UserAction.EXIT, this::exit);
        userActionHandlers.put(UserAction.LEARN, this::activateTutorialMode);
        userActionHandlers.put(UserAction.PLAY, this::playNormalGame);
        userActionHandlers.put(UserAction.CHOOSE_STRATEGY, this::changeStrategy);
        userActionHandlers.put(UserAction.STATS, this::stats);
    }

    public void handleUserAction(final UserAction userAction) {
        userActionHandlers.get(userAction).apply();
    }

    private void exit() {
        userSession.closeSession();
        terminal.dispose("Thank you for playing!");
    }

    private void changeStrategy() {
        final var strategyName = textIO.newStringInputReader()
            .withNumberedPossibleValues(StrategiesRegistry.available())
            .read("Choose strategy:");
        final var strategy = StrategiesRegistry.byName(strategyName);
        userSession.setStrategy(strategy);
    }

    private void activateTutorialMode() {
        userSession.activateTutorialMode();
        executeGameRound();
    }

    private void playNormalGame() {
        userSession.deactivateTutorialMode();
        executeGameRound();
    }

    private void executeGameRound() {
        final var userMove = textIO.newEnumInputReader(ValidMove.class)
            .withNumberedPossibleValues(ValidMove.values())
            .read("Your move:");
        final var botMove = userSession.nextMove(userMove);
        final var roundResult = processRoundResult(userMove, botMove);
        userSession.getStrategy().adjustByResult(roundResult);
    }

    private void stats() {
        terminal.printf("Stats: %s\n", userSession.getStats());
    }

    private RoundResult processRoundResult(final ValidMove userMove, final ValidMove botMove) {
        if (userSession.isTutorialMode()) {
            return processTutorial(userMove, botMove);
        } else {
            return processNormalGame(userMove, botMove);
        }
    }

    private RoundResult processTutorial(final ValidMove userMove, final ValidMove botMove) {
        final var roundResult = RoundResult.deriveRoundResult(userMove, botMove);
        if (roundResult.isWin()) {
            terminal.printf("You're doing great! Bot move was %s, and you won with move %s\n", botMove, userMove);
        } else {
            terminal.printf(
                "Bot move was %s, in order to win your move should've been %s\n",
                botMove, RoundResult.getWinningMoveFor(botMove)
            );
        }
        return roundResult;
    }

    private RoundResult processNormalGame(final ValidMove userMove, final ValidMove botMove) {
        final var roundResult = RoundResult.deriveRoundResult(userMove, botMove);
        userSession.update(userMove, botMove, roundResult);
        terminal.printf("Bot move: %s\n", botMove);
        if (roundResult.isDraw()) {
            terminal.printf("Draw\n");
        } else {
            terminal.printf("You %s\n", roundResult);
        }
        return roundResult;
    }

    @FunctionalInterface
    private interface UserActionHandler {
        void apply();
    }
}

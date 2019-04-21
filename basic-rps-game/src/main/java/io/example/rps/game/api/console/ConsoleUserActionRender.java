package io.example.rps.game.api.console;

import io.example.rps.game.model.GamingSession;
import io.example.rps.game.model.RoundResult;
import io.example.rps.game.model.UserAction;
import io.example.rps.game.model.ValidMove;
import io.example.rps.game.strategies.BasicRandomStrategy;
import io.example.rps.game.strategies.CheatingStrategy;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.EnumMap;

class ConsoleUserActionRender {
    private final EnumMap<UserAction, UserActionHandler> userActionHandlers = new EnumMap<>(UserAction.class);
    private final GamingSession userSession;
    private final TextIO textIO;
    private final TextTerminal<?> terminal;

    ConsoleUserActionRender(final GamingSession gamingSession) {
        this.userSession = gamingSession;
        textIO = TextIoFactory.getTextIO();
        terminal = textIO.getTextTerminal();
        userActionHandlers.put(UserAction.EXIT, this::exit);
        userActionHandlers.put(UserAction.LEARN, this::activateTutorialMode);
        userActionHandlers.put(UserAction.PLAY, this::playNormalGame);
        userActionHandlers.put(UserAction.STATS, this::stats);
    }

    void handleUserAction(final UserAction userAction) {
        userActionHandlers.get(userAction).apply();
    }

    private void exit() {
        userSession.closeSession();
        terminal.dispose("Thank you for playing!");
    }

    private void activateTutorialMode() {
        userSession.activateTutorialMode(new CheatingStrategy());
        executeGameRound();
    }

    private void playNormalGame() {
        userSession.deactivateTutorialMode(new BasicRandomStrategy());
        executeGameRound();
    }

    private void executeGameRound() {
        final var userMove = textIO.newEnumInputReader(ValidMove.class)
            .withNumberedPossibleValues(ValidMove.values())
            .read("Your move:");
        final var botMove = userSession.nextMove(userMove);
        processRoundResult(userMove, botMove);
    }

    private void stats() {
        terminal.printf("Stats: %s\n", userSession.getStats());
    }

    private void processRoundResult(final ValidMove userMove, final ValidMove botMove) {
        if (userSession.isTutorial()) {
            processTutorial(userMove, botMove);
        } else {
            processNormalGame(userMove, botMove);
        }
    }

    private void processTutorial(final ValidMove userMove, final ValidMove botMove) {
        final var roundResult = RoundResult.deriveRoundResult(userMove, botMove);
        if (roundResult.isWin()) {
            terminal.printf("You're doing great! Bot move was %s, and you won with move %s\n", botMove, userMove);
        } else {
            terminal.printf(
                "Bot move was %s, in order to win your move should've been %s\n",
                botMove, RoundResult.getWinningMoveFor(botMove)
            );
        }
    }

    private void processNormalGame(final ValidMove userMove, final ValidMove botMove) {
        final var roundResult = RoundResult.deriveRoundResult(userMove, botMove);
        userSession.update(userMove, botMove, roundResult);
        terminal.printf("Bot move: %s\n", botMove);
        if (roundResult.isDraw()) {
            terminal.printf("Draw\n");
        } else {
            terminal.printf("You %s\n", roundResult);
        }
    }

    @FunctionalInterface
    private interface UserActionHandler {
        void apply();
    }
}

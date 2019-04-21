package io.example.rps.game.api.console;

import io.example.rps.game.model.GamingSession;
import io.example.rps.game.model.User;
import io.example.rps.game.model.UserAction;
import io.example.rps.game.strategies.BasicRandomStrategy;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

public class ConsoleGame {
    private final TextTerminal<? extends TextTerminal<?>> terminal;
    private final TextIO textIO;
    private final GamingSession gamingSession;
    private final ConsoleUserActionRender actionRenderer;

    public ConsoleGame() {
        textIO = TextIoFactory.getTextIO();
        terminal = textIO.getTextTerminal();
        final var user = introduceUser();
        gamingSession = new GamingSession(user, new BasicRandomStrategy());
        actionRenderer = new ConsoleUserActionRender(gamingSession);
    }

    public void doPlay() {
        do {
            final var nextUserAction
                = textIO.newEnumInputReader(UserAction.class)
                .withNumberedPossibleValues(UserAction.values())
                .read("What do you want to do?");
            actionRenderer.handleUserAction(nextUserAction);
        } while (gamingSession.isActive());
    }

    private User introduceUser() {
        return new User(
            textIO.newStringInputReader().withInputTrimming(true).withMinLength(3).read("Please introduce yourself:")
        );
    }
}

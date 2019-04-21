package io.example.rps.game;

import io.example.rps.game.model.RoundResult;
import io.example.rps.game.model.ValidMove;
import io.example.rps.game.strategies.BasicRandomStrategy;
import org.beryx.textio.TextIoFactory;

public class Main {

    public static void main(final String[] args) {
        final var textIO = TextIoFactory.getTextIO();
        final var strategy = new BasicRandomStrategy();
        final var terminal = textIO.getTextTerminal();
        boolean playAgain;
        do {
            final var userMove = textIO.newEnumInputReader(ValidMove.class).read("Your move:");

            final var botMove = strategy.nextMove();
            final RoundResult roundResult = RoundResult.deriveRoundResult(userMove, botMove);
            terminal.printf("Bot move: %s\n", botMove);
            if (roundResult.isDraw()) {
                terminal.printf("Draw\n");
            } else {
                terminal.printf("You %s\n", roundResult);
            }
            playAgain = textIO.newBooleanInputReader().read("Play again?");
        } while (playAgain);
        terminal.dispose("Thank you for playing!");
//        final var password = textIO.newStringInputReader()
//            .withMinLength(6)
//            .withInputMasking(true)
//            .read("Password");
//
//        final var age = textIO.newIntInputReader()
//            .withMinVal(13)
//            .read("Age");
//
//        final var month = textIO.newEnumInputReader(Month.class)
//            .read("What month were you born in?");

//        terminal.printf("\nUser %s is %d years old, was born in %s and has the password %s.\n",
//            user, age, month, password);
    }
}

package io.example.rps.game.strategies;

import io.example.rps.game.model.RoundResult;
import io.example.rps.game.model.ValidMove;
import lombok.val;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Therefore, this is the best way to win at rock-paper-scissors:
 * if you lose the first round, switch to the thing that beats the thing your opponent just played.
 * If you win, don't keep playing the same thing, but instead switch to the thing that would beat the thing that you just played.
 * In other words, play the hand your losing opponent just played. To wit: you win a round with rock against someone else's scissors.
 * They are about to switch to paper. You should switch to scissors. Got it? Good.
 * <p>
 *
 * @see <a href="https://arstechnica.com/science/2014/05/win-at-rock-paper-scissors-by-knowing-thy-opponent/">aricle</a>
 */
public class ScienceStrategy extends GameStrategy {
    private final Random RANDOM = ThreadLocalRandom.current();
    private ValidMove lastUserMove = null;
    private ValidMove lastBotMove = null;
    private ValidMove nextBotMove = null;


    public ValidMove nextMove(final ValidMove userMove) {
        lastUserMove = userMove;
        if (nextBotMove == null) {
            final val nextMove = RANDOM.nextInt(AVAILABLE_MOVE_LENGTH);
            lastBotMove = ValidMove.values()[nextMove];
        } else {
            lastBotMove = nextBotMove;
        }
        return lastBotMove;
    }

    @Override
    public void adjustByResult(final RoundResult roundResult) {
        switch (roundResult) {
            case WIN: //user won
                assert lastUserMove != null : "Misuse: last user move should be not null";
                nextBotMove = RoundResult.getWinningMoveFor(lastUserMove);
                break;
            case LOOSE: //user lost
                assert lastBotMove != null : "Misuse: last bot move move should be not null";
                nextBotMove = RoundResult.getWinningMoveFor(lastBotMove);
                break;
            case DRAW:
                nextBotMove = lastBotMove;
                break;
        }
    }

    @Override
    public String name() {
        return "Internet-advised Bot";
    }

    @Override
    public GameStrategy get() {
        return new ScienceStrategy();
    }
}

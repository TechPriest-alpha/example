package io.example.rps.game.api.console;

import io.example.rps.game.model.GamingSession;
import io.example.rps.game.model.RoundResult;
import io.example.rps.game.model.User;
import io.example.rps.game.model.UserAction;
import io.example.rps.game.model.ValidMove;
import io.example.rps.game.strategies.CheatingStrategy;
import io.example.rps.game.strategies.FixedRockStrategy;
import org.beryx.textio.EnumInputReader;
import org.beryx.textio.StringInputReader;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

class ConsoleUserActionRenderTest {

    @Mock
    TextIO textIO;
    @Mock(answer = Answers.RETURNS_MOCKS)
    TextTerminal terminal;
    @Mock
    private StringInputReader stringReader;
    @Mock
    private EnumInputReader<ValidMove> enumReader;
    private ConsoleUserActionRender userActionRender;
    private GamingSession gamingSession;

    @BeforeEach
    void setUp() {
        initMocks(this);
        Mockito.when(textIO.getTextTerminal()).thenReturn(terminal);
        Mockito.when(textIO.newStringInputReader()).thenReturn(stringReader);
        Mockito.when(textIO.newEnumInputReader(ValidMove.class)).thenReturn(enumReader);
        Mockito.when(stringReader.withNumberedPossibleValues(Mockito.anyList())).thenReturn(stringReader);
        Mockito.when(enumReader.withNumberedPossibleValues(ValidMove.values())).thenReturn(enumReader);
        gamingSession = new GamingSession(new User(""), new FixedRockStrategy());
        userActionRender = new ConsoleUserActionRender(gamingSession, textIO);
    }

    @Test
    void testStrategyChange() {
        final var cheatingStrategy = new CheatingStrategy().name();
        Mockito.when(stringReader.read(Mockito.anyString())).thenReturn(cheatingStrategy);
        userActionRender.handleUserAction(UserAction.CHOOSE_STRATEGY);

        Mockito.verify(stringReader).read("Choose strategy:");
        assertEquals(gamingSession.getStrategy().name(), cheatingStrategy);
    }

    @Test
    void testNormalPlayWin() {
        Mockito.when(enumReader.read(Mockito.anyString())).thenReturn(ValidMove.PAPER);
        userActionRender.handleUserAction(UserAction.PLAY);

        Mockito.verify(terminal).printf("Bot move: %s\n", ValidMove.ROCK);
        Mockito.verify(terminal).printf("You %s\n", RoundResult.WIN);
    }

    @Test
    void testNormalPlayLoose() {
        Mockito.when(enumReader.read(Mockito.anyString())).thenReturn(ValidMove.SCISSORS);
        userActionRender.handleUserAction(UserAction.PLAY);

        Mockito.verify(terminal).printf("Bot move: %s\n", ValidMove.ROCK);
        Mockito.verify(terminal).printf("You %s\n", RoundResult.LOOSE);
    }

    @Test
    void testNormalPlayDraw() {
        Mockito.when(enumReader.read(Mockito.anyString())).thenReturn(ValidMove.ROCK);
        userActionRender.handleUserAction(UserAction.PLAY);

        Mockito.verify(terminal).printf("Bot move: %s\n", ValidMove.ROCK);
        Mockito.verify(terminal).printf("Draw\n");
    }
}
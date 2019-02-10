package io.example.server.core;

import io.example.auxiliary.message.chat.client.ChatCommand;
import io.example.auxiliary.message.chat.client.UnknownChatCommand;
import io.example.auxiliary.message.chat.server.HelpResponse;
import io.example.auxiliary.message.chat.types.CommandType;
import io.example.server.Routing;
import io.example.server.data.CommandResponse;
import io.example.server.data.NewCommandMessage;
import io.example.test.BaseVertxTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandHandlerTest extends BaseVertxTest {
    private CommandHandler commandHandler;

    @BeforeEach
    void initCommandHandler() {
        commandHandler = new CommandHandler(true);
        deploy(commandHandler);
    }

    @AfterEach
    void tearDown() {
        undeploy(commandHandler.deploymentID());
    }

    @Test
    void helpCommandResponded() {
        final var clientId = nextClientId("helpTest");
        addConsumer(Routing.CONNECTED_CLIENT_MANAGERS + "." + clientId.getValue());
        commandHandler.processCommand(new NewCommandMessage(clientId, new ChatCommand(clientId, CommandType.HELP)));
        assertEquals(awaitCompletion(), new CommandResponse(clientId, new HelpResponse()));
    }

    @Test
    void unknownCommandHandled() {
        final var clientId = nextClientId("unknownCommandTest");
        addConsumer(Routing.CONNECTED_CLIENT_MANAGERS + "." + clientId.getValue());
        commandHandler.processCommand(new NewCommandMessage(clientId, new UnknownChatCommand("!help", clientId)));
        assertEquals(awaitCompletion(), new CommandResponse(clientId, new HelpResponse()));
    }
}
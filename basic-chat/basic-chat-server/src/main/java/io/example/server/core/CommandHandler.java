package io.example.server.core;

import io.example.auxiliary.annotations.HandlerMethod;
import io.example.auxiliary.annotations.SpringVerticle;
import io.example.auxiliary.message.chat.client.ChatCommand;
import io.example.auxiliary.message.chat.client.UnknownChatCommand;
import io.example.auxiliary.message.chat.server.HelpResponse;
import io.example.auxiliary.message.chat.server.UnknownCommandResponse;
import io.example.auxiliary.message.chat.types.CommandType;
import io.example.server.BaseServerVerticle;
import io.example.server.Routing;
import io.example.server.data.CommandResponse;
import io.example.server.data.NewCommandMessage;
import io.vertx.core.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@SpringVerticle
public class CommandHandler extends BaseServerVerticle {

    private final boolean allowUnknownCommands;

    @Autowired
    public CommandHandler(@Value("${allow.unknown.commands}") final boolean allowUnknownCommands) {
        this.allowUnknownCommands = allowUnknownCommands;
    }

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        registerConsumer(Routing.COMMAND_HANDLER);
        super.start(startFuture);
    }

    @HandlerMethod
    public void processCommand(final NewCommandMessage newCommandMessage) {
        log.info("New command arrived: {}", newCommandMessage);
        switch (newCommandMessage.getChatCommand().getCommandType()) {

            case HELP:
                sendToClient(newCommandMessage.getClientId(), new CommandResponse(newCommandMessage.getClientId(), new HelpResponse()));
                break;
            case STATS:
                break;
            case LEAVE:
                log.debug("No special handling for 'LEAVE' command");
                break;
            case UNKNOWN:
                handleUnknownCommand(newCommandMessage);
                break;
        }
    }

    private void handleUnknownCommand(final NewCommandMessage newCommandMessage) {
        final var command = (UnknownChatCommand) newCommandMessage.getChatCommand();
        if (allowUnknownCommands) {
            final var reparseCommand = CommandType.getByPrefix(command.getCommand());
            if (reparseCommand.isUnknown()) {
                respondToUnknownCommand(newCommandMessage);
            } else {
                final var clientId = newCommandMessage.getClientId();
                log.info("Reparse unknown command from {} yielded result: {}", clientId, reparseCommand);
                processCommand(new NewCommandMessage(clientId, new ChatCommand(clientId, reparseCommand)));
            }
        } else {
            respondToUnknownCommand(newCommandMessage);
        }
    }

    private void respondToUnknownCommand(final NewCommandMessage newCommandMessage) {
        final var command = (UnknownChatCommand) newCommandMessage.getChatCommand();
        sendToClient(newCommandMessage.getClientId(), new CommandResponse(newCommandMessage.getClientId(), new UnknownCommandResponse(command.getCommand())));
        log.warn("Unexpected command: {}", newCommandMessage);
    }
}

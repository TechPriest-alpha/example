package io.example.server.core;

import io.example.auxiliary.annotations.HandlerMethod;
import io.example.auxiliary.annotations.SpringVerticle;
import io.example.auxiliary.message.chat.server.HelpResponse;
import io.example.server.BaseServerVerticle;
import io.example.server.Routing;
import io.example.server.data.CommandResponse;
import io.example.server.data.NewCommandMessage;
import io.vertx.core.Future;

@SpringVerticle
public class CommandHandler extends BaseServerVerticle {

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
            case NONE:
                log.warn("Unexpected command: {}", newCommandMessage);
                break;
        }
    }
}

package io.example.server.data;

import io.example.auxiliary.message.SupportedMessage;
import io.example.auxiliary.message.chat.client.ChatCommand;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import lombok.Getter;
import lombok.Value;

import java.util.Arrays;
import java.util.List;

@Value
public class AuthenticatedClient implements ClientConnectionCommons {
    @Getter
    private final List<SupportedMessage> supportedMessageTypes = Arrays.asList(
        new SupportedMessage<>(ChatMessage.class),
        new SupportedMessage<>(ChatCommand.class)
    );
    private final NetSocket clientConnection;
    private final String clientId;
    private final MessageConverter messageConverter;


    public void chatHandler(final Handler<Buffer> chatHandler) {
        clientConnection.handler(chatHandler);
    }

    public void disconnectHandler(final Handler<Void> disconnectHandler) {
        clientConnection.closeHandler(disconnectHandler);
    }
}

package io.example.client.api.handling;

import io.example.auxiliary.helpers.ConversionCommons;
import io.example.auxiliary.message.SupportedMessage;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.example.auxiliary.message.chat.server.AuthenticationRequest;
import io.example.auxiliary.message.chat.server.AuthenticationResult;
import io.vertx.core.Handler;
import io.vertx.core.net.NetSocket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * Class utilizes dirty trick to simplify decoding of JSON messages with varying types.
 * Known message types that would come from server are stored and incoming data is checked against known types by classname.
 */
@RequiredArgsConstructor
public class ServerConnection implements ConversionCommons {
    @Getter
    private final List<SupportedMessage> supportedMessageTypes = Arrays.asList(
        new SupportedMessage<>(ChatMessage.class),
        new SupportedMessage<>(AuthenticationRequest.class),
        new SupportedMessage<>(AuthenticationResult.class)
    );

    private final NetSocket socket;
    @Getter
    private final MessageConverter messageConverter;

    public <T extends BaseChatMessage> void sendMessage(final T message) {
        socket.write(messageConverter.encode(message));
    }

    public void close() {
        socket.close();
    }

    public void onClose(final Handler<Void> closeHandler) {
        socket.closeHandler(closeHandler);
    }
}

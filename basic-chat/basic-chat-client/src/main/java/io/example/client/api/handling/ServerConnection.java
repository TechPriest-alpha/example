package io.example.client.api.handling;

import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.example.auxiliary.message.chat.server.AuthenticationRequest;
import io.example.auxiliary.message.chat.server.AuthenticationResult;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.core.net.NetSocket;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * Class utilizes dirty trick to simplify decoding of JSON messages with varying types.
 * Known message types that would come from server are stored and incoming data is checked against known types by classname.
 */
@RequiredArgsConstructor
public class ServerConnection {
    private static final List<SupportedMessage<? extends BaseChatMessage>> supportedMessageTypes = Arrays.asList(
        new SupportedMessage<>(ChatMessage.class),
        new SupportedMessage<>(AuthenticationRequest.class),
        new SupportedMessage<>(AuthenticationResult.class)
    );

    private final NetSocket socket;
    private final MessageConverter messageConverter;

    public <T extends BaseChatMessage> void sendMessage(final T message) {
        socket.write(messageConverter.encode(message));
    }

    public <T extends BaseChatMessage> T decode(final Buffer data) {
        //noinspection unchecked
        return (T) supportedMessageTypes.stream()
            .filter(supportedMessage -> data.toString().toLowerCase().startsWith(supportedMessage.getClassName()))
            .findFirst()
            .map(supportedMessage -> Json.decodeValue(data, supportedMessage.getCls()))
            .orElseThrow();
    }
}

package io.example.server.data;

import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.core.net.NetSocket;

public interface ClientConnectionCommons extends MessageConverter {
    default void sendToClient(final String data) {
        getClientConnection().write(data);
    }

    default void sendToClient(final BaseChatMessage data) {
        sendToClient(Json.encode(data));
    }

    @Override
    default <T extends BaseChatMessage> T decode(final Buffer encoded, final Class<T> messageType) {
        return getMessageConverter().decode(encoded, messageType);
    }

    @Override
    default <T extends BaseChatMessage> T decode(final String encoded, final Class<T> messageType) {
        return getMessageConverter().decode(encoded, messageType);
    }

    @Override
    default <T extends BaseChatMessage> String encode(final T message) {
        return getMessageConverter().encode(message);
    }

    NetSocket getClientConnection();

    MessageConverter getMessageConverter();
}

package io.example.server.data;

import io.example.auxiliary.helpers.ConversionCommons;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.vertx.core.json.Json;
import io.vertx.core.net.NetSocket;

public interface ClientConnectionCommons extends ConversionCommons {
    default void sendToClient(final String data) {
        getClientConnection().write(data);
    }

    default void sendToClient(final BaseChatMessage data) {
        sendToClient(Json.encode(data));
    }

    NetSocket getClientConnection();
}

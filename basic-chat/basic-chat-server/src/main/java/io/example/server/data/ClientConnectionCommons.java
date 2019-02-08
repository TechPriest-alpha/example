package io.example.server.data;

import io.example.auxiliary.helpers.ConversionCommons;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.vertx.core.json.Json;

public interface ClientConnectionCommons extends ConversionCommons {
    void sendToClient(final String data);

    default void sendToClient(final BaseChatMessage data) {
        sendToClient(Json.encode(data));
    }

}

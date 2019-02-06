package io.example.server;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.internal.BaseInternalMessage;

public abstract class BaseServerVerticle extends BaseVerticle {

    protected void sendToClient(final ClientId clientId, final BaseInternalMessage messageToClient) {
        sendMessage(Routing.CONNECTED_CLIENT_MANAGERS + "." + clientId.getValue(), messageToClient);
    }
}

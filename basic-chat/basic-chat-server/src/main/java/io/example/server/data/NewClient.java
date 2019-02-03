package io.example.server.data;

import io.example.auxiliary.message.chat.AuthRequest;
import io.example.auxiliary.message.internal.BaseInternalMessage;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import lombok.Value;

@Value
public class NewClient implements ClientConnectionCommons, BaseInternalMessage {
    private final NetSocket clientConnection;

    public void sendAuthenticationRequest(final String authMessageText) {
        sendToClient(new AuthRequest(authMessageText));
    }

    public void authenticationHandler(final Handler<Buffer> authenticator) {
        clientConnection.handler(authenticator);
    }

    public void resume() {
        clientConnection.resume();
    }
}

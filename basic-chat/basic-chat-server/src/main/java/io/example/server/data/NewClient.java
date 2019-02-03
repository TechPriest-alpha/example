package io.example.server.data;

import io.example.auxiliary.message.chat.AuthRequest;
import io.example.auxiliary.message.chat.AuthResponse;
import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.example.auxiliary.message.internal.BaseInternalMessage;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import lombok.Value;

@Value
public class NewClient implements ClientConnectionCommons, BaseInternalMessage {
    private final NetSocket clientConnection;
    private final MessageConverter messageConverter;

    public void sendAuthenticationRequest(final String authMessageText) {
        sendToClient(new AuthRequest(authMessageText));
    }

    public void authenticationHandler(final Handler<Buffer> authenticator) {
        clientConnection.handler(authenticator);
    }

    public void resume() {
        clientConnection.resume();
    }

    public ClientInfo toAuthenticatedClient(final AuthResponse authResponse) {
        return new ClientInfo(clientConnection, authResponse.getClientId(), messageConverter);
    }
}

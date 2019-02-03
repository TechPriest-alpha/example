package io.example.server.data;

import io.example.auxiliary.message.chat.client.AuthenticationResponse;
import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.example.auxiliary.message.chat.server.AuthenticationRequest;
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
        sendToClient(new AuthenticationRequest(authMessageText));
    }

    public void authenticationHandler(final Handler<Buffer> authenticator) {
        clientConnection.handler(authenticator);
    }

    public void resume() {
        clientConnection.resume();
    }

    public ClientInfo toAuthenticatedClient(final AuthenticationResponse authenticationResponse) {
        return new ClientInfo(clientConnection, authenticationResponse.getClientId(), messageConverter);
    }
}

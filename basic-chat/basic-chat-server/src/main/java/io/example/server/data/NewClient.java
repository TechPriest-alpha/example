package io.example.server.data;

import io.example.auxiliary.message.SupportedMessage;
import io.example.auxiliary.message.chat.client.AuthenticationResponse;
import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.example.auxiliary.message.chat.server.AuthenticationRequest;
import io.example.auxiliary.message.internal.BaseInternalMessage;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import lombok.Getter;
import lombok.Value;

import java.util.Arrays;
import java.util.List;

@Value
public class NewClient implements ClientConnectionCommons, BaseInternalMessage {
    @Getter
    private final List<SupportedMessage> supportedMessageTypes = Arrays.asList(
        new SupportedMessage<>(AuthenticationResponse.class)
    );
    private final NetSocket clientConnection;
    private final MessageConverter messageConverter;

    public void sendAuthenticationRequest() {
        sendToClient(new AuthenticationRequest());
    }

    public void authenticationHandler(final Handler<Buffer> authenticator) {
        clientConnection.handler(authenticator);
    }

    public void resume() {
        clientConnection.resume();
    }

    public AuthenticatedClient toAuthenticatedClient(final AuthenticationResponse authenticationResponse) {
        return new AuthenticatedClient(clientConnection, authenticationResponse.getClientId(), messageConverter);
    }
}

package io.example.server.contracts;

import io.example.auxiliary.message.chat.client.AuthenticationResponse;
import io.example.auxiliary.message.internal.BaseInternalMessage;
import io.example.server.data.ClientConnectionCommons;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;

public interface NewClientContract extends ClientConnectionCommons, BaseInternalMessage {
    void sendAuthenticationRequest();

    void authenticationHandler(final Handler<Buffer> authenticator);

    AuthenticatedClientContract toAuthenticatedClient(final AuthenticationResponse authenticationResponse);
}

package io.example.server.contracts;

import io.example.auxiliary.message.ClientId;
import io.example.server.data.ClientConnectionCommons;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;

public interface AuthenticatedClientContract extends ClientConnectionCommons {
    void chatHandler(final Handler<Buffer> chatHandler);

    void disconnectHandler(final Handler<Void> disconnectHandler);

    ClientId getClientId();
}

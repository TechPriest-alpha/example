package io.example.client.api;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.annotations.SpringVerticle;
import io.vertx.core.Future;

@SpringVerticle
public class ChatTcpClient extends BaseVerticle {

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        vertx.createNetClient().connect(8123, "127.0.0.1", new ChatClientHandler(vertx));
        super.start(startFuture);
    }
}

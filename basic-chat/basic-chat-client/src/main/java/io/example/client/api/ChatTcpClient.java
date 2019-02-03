package io.example.client.api;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.annotations.SpringVerticle;
import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@SpringVerticle
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ChatTcpClient extends BaseVerticle {

    private final MessageConverter messageConverter;

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        vertx.createNetClient().connect(8123, "127.0.0.1", new ChatClientHandler(vertx, messageConverter));
        super.start(startFuture);
    }
}

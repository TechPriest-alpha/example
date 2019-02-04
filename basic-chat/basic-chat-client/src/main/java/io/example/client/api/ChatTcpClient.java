package io.example.client.api;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.annotations.SpringVerticle;
import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.example.client.api.handling.ChatClientHandler;
import io.vertx.core.Future;
import io.vertx.core.net.NetClientOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@SpringVerticle(instances = 1)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ChatTcpClient extends BaseVerticle {

    private final MessageConverter messageConverter;

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        vertx
            .createNetClient(new NetClientOptions().setConnectTimeout(10_000).setReconnectAttempts(5).setReconnectInterval(1000))
            .connect(8123, "127.0.0.1", new ChatClientHandler(vertx, messageConverter));
        super.start(startFuture);
    }
}

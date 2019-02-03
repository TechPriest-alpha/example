package io.example.client.api;

import io.example.auxiliary.dto.ChatMessage;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.core.net.NetSocket;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Value
public class DataHandler implements Handler<Buffer> {
    private static final Logger log = LoggerFactory.getLogger(ChatClientHandler.class);
    private final NetSocket socket;

    @Override
    public void handle(final Buffer event) {
        log.info("Msg: {}", Json.decodeValue(event, ChatMessage.class));
        socket.write(Json.encode(new ChatMessage("Hello from Client")));
    }
}

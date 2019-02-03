package io.example.server.api.tcp;

import io.example.auxiliary.message.chat.ChatMessage;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataHandler implements Handler<Buffer> {
    private static final Logger log = LoggerFactory.getLogger(DataHandler.class);

    @Override
    public void handle(final Buffer event) {
        log.info("{}", Json.decodeValue(event, ChatMessage.class));
    }
}

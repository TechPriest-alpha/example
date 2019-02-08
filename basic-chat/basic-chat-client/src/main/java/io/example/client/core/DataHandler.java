package io.example.client.core;

import io.example.auxiliary.BaseVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.parsetools.RecordParser;

import static io.example.client.api.server.ChatTcpClient.DELIMITER;

public abstract class DataHandler extends BaseVerticle implements Handler<Buffer> {

    private final RecordParser recordParser = RecordParser.newDelimited(DELIMITER);

    @Override
    public void handle(final Buffer event) {
        recordParser.handler(this::doHandle).handle(event);
    }

    protected abstract void doHandle(final Buffer event);
}

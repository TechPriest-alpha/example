package io.example.server.api.tcp;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.parsetools.RecordParser;

public abstract class ServerSocketHandler implements Handler<Buffer> {
    private final RecordParser recordParser = RecordParser.newDelimited(TcpServer.DELIMITER);

    @Override
    public void handle(final Buffer event) {
        recordParser.handler(this::doHandle).handle(event);
    }

    protected abstract void doHandle(final Buffer event);
}

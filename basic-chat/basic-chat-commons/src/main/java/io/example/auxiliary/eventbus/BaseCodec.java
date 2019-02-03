package io.example.auxiliary.eventbus;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class BaseCodec implements MessageCodec<Object, Object> {
    @Override
    public void encodeToWire(final Buffer buffer, final Object o) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Object decodeFromWire(final int pos, final Buffer buffer) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Object transform(final Object o) {
        return o;
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}

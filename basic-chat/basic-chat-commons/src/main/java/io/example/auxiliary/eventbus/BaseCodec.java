package io.example.auxiliary.eventbus;

import io.example.auxiliary.message.internal.BaseInternalMessage;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class BaseCodec implements MessageCodec<BaseInternalMessage, BaseInternalMessage> {
    @Override
    public void encodeToWire(final Buffer buffer, final BaseInternalMessage o) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public BaseInternalMessage decodeFromWire(final int pos, final Buffer buffer) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public BaseInternalMessage transform(final BaseInternalMessage o) {
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

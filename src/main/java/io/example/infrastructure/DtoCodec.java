package io.example.infrastructure;

import io.example.dto.DtoMarker;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

/**
 * @author Viktor
 * @since 2018-10-27
 */
public class DtoCodec implements MessageCodec<DtoMarker, DtoMarker> {
    @Override
    public void encodeToWire(final Buffer buffer, final DtoMarker dtoMarker) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DtoMarker decodeFromWire(final int pos, final Buffer buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DtoMarker transform(final DtoMarker dtoMarker) {
        return dtoMarker;
    }

    @Override
    public String name() {
        return "dtoCodec";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}

package io.example.client.core;

import io.example.auxiliary.BaseVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;

public abstract class DataHandler extends BaseVerticle implements Handler<Buffer> {
}

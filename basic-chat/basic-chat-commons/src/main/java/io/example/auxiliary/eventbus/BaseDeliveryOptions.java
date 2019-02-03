package io.example.auxiliary.eventbus;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;

public class BaseDeliveryOptions extends DeliveryOptions {
    public BaseDeliveryOptions() {
        super();
    }

    public BaseDeliveryOptions(final DeliveryOptions other) {
        super(other);
    }

    public BaseDeliveryOptions(final JsonObject json) {
        super(json);
    }

    @Override
    public String getCodecName() {
        return BaseCodec.class.getSimpleName();
    }
}

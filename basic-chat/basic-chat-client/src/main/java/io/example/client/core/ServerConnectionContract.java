package io.example.client.core;

import io.example.auxiliary.helpers.ConversionCommons;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.vertx.core.Handler;

public interface ServerConnectionContract extends ConversionCommons {

    void close();

    void onClose(final Handler<Void> closeHandler);

    <T extends BaseChatMessage> void sendMessage(final T message);
}

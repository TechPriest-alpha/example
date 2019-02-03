package io.example.auxiliary.message.chat.conversion;

import io.example.auxiliary.message.chat.BaseChatMessage;
import io.vertx.core.buffer.Buffer;

/**
 * Should allow Qualifier-based injection of API-specific message encoding support.
 */
public interface MessageConverter {
    <T extends BaseChatMessage> T decode(final Buffer encoded, final Class<T> messageType);

    <T extends BaseChatMessage> T decode(final String encoded, final Class<T> messageType);

    <T extends BaseChatMessage> String encode(final T message);
}

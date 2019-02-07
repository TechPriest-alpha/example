package io.example.auxiliary.helpers;

import io.example.auxiliary.message.SupportedMessage;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.example.auxiliary.message.chat.server.AnyMessage;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;

import java.util.List;

public interface ConversionCommons {

    default <T extends BaseChatMessage> T decode(final Buffer encoded, final Class<T> messageType) {
        return getMessageConverter().decode(encoded, messageType);
    }

    default <T extends BaseChatMessage> T decode(final String encoded, final Class<T> messageType) {
        return getMessageConverter().decode(encoded, messageType);
    }

    default <T extends BaseChatMessage> T decode(final Buffer data) {
        //noinspection unchecked
        return (T) getSupportedMessageTypes().stream()
            .filter(supportedMessage -> data.toString().toLowerCase().startsWith(supportedMessage.getClassName()))
            .findFirst()
            .map(supportedMessage -> Json.decodeValue(data, supportedMessage.getCls()))
            .orElseGet(() -> fallbackDecode(data));
    }

    default BaseChatMessage fallbackDecode(final Buffer data) {
        try {
            //strip class info
            return Json.decodeValue(data.toString().substring(data.toString().indexOf(":") + 1, data.length() - 1), AnyMessage.class);
        } catch (final Exception ex) {
            return BaseChatMessage.NULL_MESSAGE;
        }
    }

    List<SupportedMessage> getSupportedMessageTypes();

    default <T extends BaseChatMessage> String encode(final T message) {
        return getMessageConverter().encode(message);
    }

    MessageConverter getMessageConverter();
}

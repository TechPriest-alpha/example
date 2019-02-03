package io.example.auxiliary.message.chat.conversion;

import io.example.auxiliary.message.chat.BaseChatMessage;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("tcp-message-converter")
public class JsonMessageConverter implements MessageConverter {
    @Override
    public <T extends BaseChatMessage> T decode(final Buffer encoded, final Class<T> messageType) {
        return Json.decodeValue(encoded, messageType);
    }

    @Override
    public <T extends BaseChatMessage> T decode(final String encoded, final Class<T> messageType) {
        return Json.decodeValue(encoded, messageType);
    }

    @Override
    public <T extends BaseChatMessage> String encode(final T message) {
        return Json.encode(message);
    }
}

package io.example.auxiliary.message;

import io.example.auxiliary.message.chat.BaseChatMessage;
import lombok.Getter;

@Getter
public class SupportedMessage<T extends BaseChatMessage> {
    private final Class<T> cls;
    private final String className;

    public SupportedMessage(final Class<T> cls) {
        this.cls = cls;
        this.className = "{\"" + cls.getSimpleName().toLowerCase();
    }
}

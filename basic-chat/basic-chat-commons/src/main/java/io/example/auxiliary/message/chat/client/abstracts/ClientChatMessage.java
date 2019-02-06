package io.example.auxiliary.message.chat.client.abstracts;

import io.example.auxiliary.message.chat.BaseChatMessage;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public abstract class ClientChatMessage extends BaseChatMessage {
    @Override
    protected String messageKey() {
        return null;
    }
}

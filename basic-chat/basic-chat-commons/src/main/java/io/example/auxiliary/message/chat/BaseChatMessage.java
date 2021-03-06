package io.example.auxiliary.message.chat;

import io.example.auxiliary.message.chat.types.MessageType;
import lombok.EqualsAndHashCode;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Marker interface for application messages participating in server-client exchange.
 */
@EqualsAndHashCode
public abstract class BaseChatMessage {
    private static final ResourceBundle MESSAGE_BUNDLE = ResourceBundle.getBundle("messages/CommonMessage");

    public abstract String getMessage();

    protected String message(final String key, final Object... args) {
        if (key != null && !key.isBlank()) {
            return MessageFormat.format(MESSAGE_BUNDLE.getString(key), args);
        } else {
            return "";
        }
    }

    protected String message(final Object... args) {
        return message(messageKey(), args);
    }

    protected abstract String messageKey();

    public abstract MessageType getMessageType();

    public static final BaseChatMessage NULL_MESSAGE = new BaseChatMessage() {
        @Override
        public String getMessage() {
            return "NULL_MESSAGE";
        }

        @Override
        protected String messageKey() {
            return null;
        }

        @Override
        public MessageType getMessageType() {
            return MessageType.NONE;
        }
    };

}

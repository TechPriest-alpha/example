package io.example.auxiliary.message.chat;

import io.example.auxiliary.message.chat.types.MessageType;

/**
 * Marker interface for application messages participating in server-client exchange.
 */
public interface BaseChatMessage {
    MessageType getMessageType();
}

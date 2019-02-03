package io.example.auxiliary.message.chat;

/**
 * Marker interface for application messages participating in server-client exchange.
 */
public interface BaseChatMessage {
    MessageType getMessageType();
}

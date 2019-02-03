package io.example.auxiliary.message.chat;

public enum MessageType {
    AUTHENTICATION_REQUEST, AUTHENTICATION_RESPONSE, CHAT_TEXT;

    public boolean isAuthenticationRequest() {
        return AUTHENTICATION_REQUEST == this;
    }
}

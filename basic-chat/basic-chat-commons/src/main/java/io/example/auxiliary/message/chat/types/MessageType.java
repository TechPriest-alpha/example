package io.example.auxiliary.message.chat.types;

public enum MessageType {
    AUTHENTICATION_REQUEST, AUTHENTICATION_RESPONSE, AUTHENTICATION_RESULT, CHAT_TEXT, COMMAND;

    public boolean isAuthenticationRequest() {
        return AUTHENTICATION_REQUEST == this;
    }

    public boolean isAuthenticationResult() {
        return AUTHENTICATION_RESULT == this;
    }
}

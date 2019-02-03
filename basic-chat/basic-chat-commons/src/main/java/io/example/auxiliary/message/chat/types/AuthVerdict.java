package io.example.auxiliary.message.chat.types;

public enum AuthVerdict {
    NAME_ALREADY_REGISTERED, SUCCESS;

    public boolean success() {
        return SUCCESS == this;
    }
}

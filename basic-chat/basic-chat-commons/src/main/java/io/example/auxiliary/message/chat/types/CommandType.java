package io.example.auxiliary.message.chat.types;

public enum CommandType {
    HELP("!help"), STATS("!stats"), LEAVE("!leave"), NONE("");

    private final String prefix;

    CommandType(final String prefix) {
        this.prefix = prefix;
    }

    public boolean isNone() {
        return NONE == this;
    }

    public static CommandType getByPrefix(final String userInput) {
        for (final CommandType cmd : values()) {
            if (userInput.startsWith(cmd.prefix)) {
                return cmd;
            }
        }
        return NONE;
    }

    public boolean isNotLeave() {
        return LEAVE != this;
    }
}

package io.example.client.core;

import io.example.client.api.server.handling.ServerConnection;

public class AutomatedDataHandlerFactory implements DataHandlerFactory {
    @Override
    public DataHandler create(final ServerConnection serverConnection, final boolean allowUnknownCommands) {
        return new AutomatedDataHandler(serverConnection, allowUnknownCommands);
    }
}

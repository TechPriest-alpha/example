package io.example.client.core;

import io.example.client.api.server.handling.ServerConnection;

public interface DataHandlerFactory {

    DataHandler create(final ServerConnection serverConnection, final boolean allowUnknownCommands);
}

package io.example.client.core;

import io.example.client.api.server.handling.TcpServerConnection;

public interface DataHandlerFactory {

    DataHandler create(final TcpServerConnection tcpServerConnection, final boolean allowUnknownCommands);
}

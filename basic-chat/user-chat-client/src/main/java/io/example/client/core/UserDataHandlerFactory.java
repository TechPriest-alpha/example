package io.example.client.core;

import io.example.client.api.server.handling.TcpServerConnection;

public class UserDataHandlerFactory implements DataHandlerFactory {
    @Override
    public DataHandler create(final TcpServerConnection tcpServerConnection, final boolean allowUnknownCommands) {
        return new UserDataHandler(tcpServerConnection, allowUnknownCommands);
    }
}

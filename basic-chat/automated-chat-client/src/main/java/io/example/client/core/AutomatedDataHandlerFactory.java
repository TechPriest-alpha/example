package io.example.client.core;

import io.example.client.api.server.handling.TcpServerConnection;

public class AutomatedDataHandlerFactory implements DataHandlerFactory {
    @Override
    public DataHandler create(final TcpServerConnection tcpServerConnection, final boolean allowUnknownCommands) {
        return new AutomatedDataHandler(tcpServerConnection, allowUnknownCommands);
    }
}

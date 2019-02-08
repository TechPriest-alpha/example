package io.example.client.api;

import io.example.auxiliary.annotations.SpringVerticle;
import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.example.client.api.server.ChatTcpClient;
import io.example.client.core.DataHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@SpringVerticle(instances = 800)
public class ExtendedChatTcpClient extends ChatTcpClient {
    @Autowired
    public ExtendedChatTcpClient(
        final MessageConverter messageConverter,
        @Value("${server.host}") final String serverHost,
        @Value("${server.port}") final int serverPort,
        @Value("${allow.unknown.commands:true}") final boolean allowUnknownCommands,
        final DataHandlerFactory dataHandlerFactory
    ) {
        super(messageConverter, serverHost, serverPort, allowUnknownCommands, dataHandlerFactory);
    }
}

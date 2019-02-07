package io.example.client.core;

import io.example.auxiliary.message.chat.conversion.JsonMessageConverter;
import io.example.client.api.server.handling.ServerConnection;
import io.example.test.BaseVertxTest;
import io.vertx.core.net.NetSocket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

class UserDataHandlerTest extends BaseVertxTest {

    private UserDataHandler userDataHandler;
    private NetSocket socket;

    @BeforeEach
    void setUp() {
        socket = Mockito.mock(NetSocket.class);
        userDataHandler = new UserDataHandler(new ServerConnection(socket, new JsonMessageConverter()), true);

    }

    @AfterEach
    void tearDown() {
        undeploy(userDataHandler.deploymentID());
    }
}
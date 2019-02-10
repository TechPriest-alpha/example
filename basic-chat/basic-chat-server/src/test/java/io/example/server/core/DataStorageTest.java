package io.example.server.core;

import io.example.auxiliary.message.chat.client.ChatCommand;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.types.CommandType;
import io.example.server.data.NewChatMessage;
import io.example.test.BaseVertxTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataStorageTest extends BaseVertxTest {
    private DataStorage dataStorage;

    @BeforeEach
    void setUp() {
        dataStorage = new DataStorage();
    }

    @Test
    void lastMessagesFoundCorrectly() {
        final var clientId = nextClientId("lastMessages1");
        final LinkedList<ChatMessage> validation = new LinkedList<>();
        for (int i = 0; i < DataStorage.MAX_RECENT_MESSAGES_TO_KEEP; i++) {
            final var chatMessage = new ChatMessage(RandomStringUtils.randomAlphabetic(10), clientId);
            if (i > DataStorage.MAX_RECENT_MESSAGES_TO_KEEP - DataStorage.LAST_MESSAGE_CHUNK_SIZE - 1) {
                validation.addFirst(chatMessage);
            }
            dataStorage.onNewMessage(new NewChatMessage(clientId, chatMessage));
        }

        final var lastMessages = dataStorage.lastMessages();
        assertEquals(lastMessages, validation);
    }

    @Test
    void onlyChatMessagesReturned() {
        final var clientId = nextClientId("lastMessages2");
        for (int i = 0; i < DataStorage.LAST_MESSAGE_CHUNK_SIZE; i++) {
            final NewChatMessage chatMessage;
            if (i % 2 == 0) {
                chatMessage = new NewChatMessage(clientId, new ChatMessage(RandomStringUtils.randomAlphabetic(10), clientId));
            } else {
                chatMessage = new NewChatMessage(clientId, new ChatCommand(clientId, CommandType.STATS));
            }
            dataStorage.onNewMessage(chatMessage);
        }

        final var lastMessages = dataStorage.lastMessages();
        //noinspection PointlessArithmeticExpression: required if chunk size changes to odd
        assertEquals(lastMessages.size(), DataStorage.LAST_MESSAGE_CHUNK_SIZE / 2 + DataStorage.LAST_MESSAGE_CHUNK_SIZE % 2);
    }
}
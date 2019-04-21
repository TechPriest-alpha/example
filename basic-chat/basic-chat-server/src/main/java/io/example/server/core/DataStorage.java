package io.example.server.core;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.annotations.HandlerMethod;
import io.example.auxiliary.annotations.SpringVerticle;
import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.server.Routing;
import io.example.server.data.DisconnectedClient;
import io.example.server.data.NewChatMessage;
import io.vertx.core.Future;
import io.vertx.core.impl.ConcurrentHashSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;

@SpringVerticle(instances = 1)
public class DataStorage extends BaseVerticle {
    public static final int LAST_MESSAGE_CHUNK_SIZE = 100;
    public static final int MAX_RECENT_MESSAGES_TO_KEEP = LAST_MESSAGE_CHUNK_SIZE * 5;

    private final ConcurrentHashSet<ClientId> REGISTERED_CLIENTS = new ConcurrentHashSet<>();
    private final ConcurrentLinkedDeque<ChatMessage> recentMessages = new ConcurrentLinkedDeque<>();
    private final ConcurrentMap<ClientId, List<BaseChatMessage>> allMessages = new ConcurrentHashMap<>();

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        registerConsumer(Routing.MESSAGE_STORAGE);
        super.start(startFuture);
    }

    @HandlerMethod
    public void onNewMessage(final NewChatMessage newChatMessage) {
        if (newChatMessage.getChatMessage().getMessageType().isChatMessage()) {
            recentMessages.addFirst((ChatMessage) newChatMessage.getChatMessage());
            final int startSize = recentMessages.size();
            if (startSize > MAX_RECENT_MESSAGES_TO_KEEP) {
                discardOldEntries(startSize);
            }
        }
        allMessages.merge(newChatMessage.getClientId(), new ArrayList<>(), (clientId, clientMessages) -> {
            clientMessages.add(newChatMessage.getChatMessage());
            return clientMessages;
        });
        log.debug("New message from {} stored", newChatMessage.getClientId());
    }

    private void discardOldEntries(final int startSize) {
        final var messagesToRemove = startSize - LAST_MESSAGE_CHUNK_SIZE;
        for (int i = 0; i < messagesToRemove && recentMessages.size() > LAST_MESSAGE_CHUNK_SIZE + 1; i++) {
            recentMessages.pollLast();
        }
    }

    @HandlerMethod
    public void onDisconnectingClient(final DisconnectedClient disconnectedClient) {
        REGISTERED_CLIENTS.remove(disconnectedClient.getClientId());
        allMessages.remove(disconnectedClient.getClientId());
    }

    public boolean containsClient(final ClientId clientId) {
        return REGISTERED_CLIENTS.contains(clientId);
    }

    public boolean addClient(final ClientId clientId) {
        return REGISTERED_CLIENTS.add(clientId);
    }

    public List<ChatMessage> lastMessages() {
        final ChatMessage[] recentMessagesArray = recentMessages.toArray(new ChatMessage[0]);
        return recentMessagesArray.length > LAST_MESSAGE_CHUNK_SIZE
            ? Arrays.asList(Arrays.copyOf(recentMessagesArray, LAST_MESSAGE_CHUNK_SIZE))
            : Arrays.asList(recentMessagesArray);
    }
}

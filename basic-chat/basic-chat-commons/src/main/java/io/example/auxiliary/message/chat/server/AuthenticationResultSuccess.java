package io.example.auxiliary.message.chat.server;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.server.abstracts.AuthenticationResult;
import io.example.auxiliary.message.chat.types.AuthVerdict;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.beans.ConstructorProperties;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class AuthenticationResultSuccess extends AuthenticationResult {

    @ConstructorProperties({"clientId", "lastMessages"})
    public AuthenticationResultSuccess(final ClientId clientId, final List<ChatMessage> lastMessages) {
        super(clientId, AuthVerdict.SUCCESS, lastMessages);
    }

    @ConstructorProperties({"message", "clientId", "verdict", "lastMessages"})
    private AuthenticationResultSuccess(final String message, final ClientId clientId, final AuthVerdict verdict, final List<ChatMessage> lastMessages) {
        super(message, clientId, verdict, lastMessages);
    }

    @Override
    protected String messageKey() {
        return "authentication.result.success";
    }
}

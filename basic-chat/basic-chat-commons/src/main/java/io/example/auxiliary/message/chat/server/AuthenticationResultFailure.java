package io.example.auxiliary.message.chat.server;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.chat.server.abstracts.AuthenticationResult;
import io.example.auxiliary.message.chat.types.AuthVerdict;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.beans.ConstructorProperties;
import java.util.Collections;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class AuthenticationResultFailure extends AuthenticationResult {
    @ConstructorProperties({"clientId"})
    public AuthenticationResultFailure(final ClientId clientId) {
        super(clientId, AuthVerdict.NAME_ALREADY_REGISTERED, Collections.emptyList());
    }

    @Override
    protected String messageKey() {
        return "authentication.result.failure";
    }
}

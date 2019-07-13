package io.example.integration.internal.int_service_example;

import io.example.business.api.CommonActions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class IntServiceExampleIncomingGateway {
    private final CommonActions commonActions;
}

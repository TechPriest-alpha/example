package io.example.integration.external.ext_service_example;

import io.example.business.api.CommonActions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExtServiceExampleIncomingGateway {
    private final CommonActions commonActions;
}

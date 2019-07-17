package io.example.ext_service_example;

import io.example.business.api.CommonBusinessActions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExtServiceExampleIncomingGateway {
    private final CommonBusinessActions commonActions;
}

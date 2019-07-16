package io.example.int_service_example;

import io.example.business.CommonBusinessActions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class IntServiceExampleIncomingGateway {
    private final CommonBusinessActions commonActions;
}

package io.example.ext_service_example;

import io.example.ext_service_example.client.rest.RestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExtServiceExampleOutgoingGateway {
    private final RestClient restClient;
}

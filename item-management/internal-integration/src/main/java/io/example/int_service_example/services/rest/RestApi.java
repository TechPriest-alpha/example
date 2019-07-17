package io.example.int_service_example.services.rest;

import io.example.int_service_example.IntServiceExampleIncomingGateway;
import io.example.int_service_example.data.IntServiceRequest;
import io.example.int_service_example.data.IntServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RestApi {
    private final IntServiceExampleIncomingGateway intServiceExampleIncomingGateway;

    @RequestMapping("/requestData")
    public IntServiceResponse handleRequest(final IntServiceRequest intServiceRequest) {
        return new IntServiceResponse();
    }
}

package io.example.ext_service_example.services.rest;

import io.example.ext_service_example.ExtServiceExampleIncomingGateway;
import io.example.ext_service_example.data.ExtServiceRequest;
import io.example.ext_service_example.data.ExtServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RestApi {
    private final ExtServiceExampleIncomingGateway extServiceExampleIncomingGateway;

    @RequestMapping("/requestData")
    public ExtServiceResponse handleRequest(final ExtServiceRequest extServiceRequest) {
        return new ExtServiceResponse();
    }
}

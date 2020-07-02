package io.example.quarkus;


import io.example.quarkus.services.HelloService;
import io.quarkus.runtime.Quarkus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

@Slf4j
@Path("/main")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class Main {

    private final HelloService helloService;
    private final Event<String> eventSender;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hi/{name}")
    public void hi(final @PathParam(value = "name") @DefaultValue("everyone") String name, @Suspended final AsyncResponse ar) {
        eventSender.fireAsync(name).handle((result, error) -> {
            log.info("Result: {}", result);
            ar.resume(helloService.hello(name));
            return null;
        });
    }
}

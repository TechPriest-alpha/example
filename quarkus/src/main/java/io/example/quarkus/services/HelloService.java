package io.example.quarkus.services;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@Slf4j
@ApplicationScoped
public class HelloService {

    @Any
    @Inject
    Instance<SomeAPI> apis;

    public String hello(final String name) {
        return "Hello " + name + "!";
    }

    public void handle(final @ObservesAsync String data) {
        log.info("Event fired: {}, apis: {}", data, apis.stream().count());
    }
}

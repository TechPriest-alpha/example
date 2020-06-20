package io.example.quarkus.services;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;

@Slf4j
@ApplicationScoped
public class HelloService {

    public String hello(final String name) {
        return "Hello " + name + "!";
    }

    public void handle(final @ObservesAsync String data) {
        log.info("Event fired: {}", data);
    }
}

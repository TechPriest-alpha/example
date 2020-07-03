package org.example;

import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.validation.Validated;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;

@Slf4j
@Singleton
@Validated
public class HelperService implements Api {

    @EventListener
    public void eventListener(final String event) {
        log.info("Event: {}", event);
    }

    public String output() {
        return "Wow11! ";
    }
}

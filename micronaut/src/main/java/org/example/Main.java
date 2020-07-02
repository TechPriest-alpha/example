package org.example;

import io.micronaut.runtime.Micronaut;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class Main {
    public static void main(final String[] args) {

        do {
            log.info("Starting");
            val context = Micronaut.run(Main.class);
            log.info("Started");
            Lifecycle.RESTART.set(false);
            try {
                log.info("waiting");
                synchronized (Lifecycle.RESTART) {
                    while (!Lifecycle.RESTART.get()) {
                        Lifecycle.RESTART.wait();
                    }
                }
                log.info("restarting: {}, {}", Lifecycle.RESTART.get(), Lifecycle.RUN.get());
                context.stop();

                log.info("Context stopped");
            } catch (final Exception ex) {
                log.info("err");
            }
        } while (Lifecycle.RUN.get());
        log.info("exiting");
    }
}

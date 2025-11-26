package com.example.temporal;

import java.time.Instant;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExampleActivityImpl implements ExampleActivity {
    @Override
    public ExecOutput handle(ExecInput execInput) {
        log.debug("Received: {}", execInput);
        return new ExecOutput(
            execInput.id(),
            "Processed: %s".formatted(execInput.input()),
            Instant.now()
        );
    }
}

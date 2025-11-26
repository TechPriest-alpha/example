package com.example.temporal;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExampleWorkflowAPIImpl implements ExampleWorkflowAPI {
    private static final ActivityOptions options = ActivityOptions.newBuilder()
        .setStartToCloseTimeout(Duration.ofSeconds(60))
        .build();

    private final ExampleActivity activity = Workflow.newActivityStub(ExampleActivity.class, options);

    @Override
    public ExecOutput execute(ExecInput execInput) {
        log.debug("Executing: {}", execInput);
        return activity.handle(execInput);
    }
}

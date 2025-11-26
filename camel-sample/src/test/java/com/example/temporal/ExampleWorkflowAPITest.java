package com.example.temporal;

import io.temporal.client.WorkflowOptions;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.testing.TestWorkflowExtension;
import io.temporal.testing.TestWorkflowRule;
import io.temporal.worker.Worker;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.assertj.core.api.Assertions.assertThat;

class ExampleWorkflowAPITest {
    @RegisterExtension
    public static final TestWorkflowExtension testWorkflowRule =
        TestWorkflowExtension.newBuilder()
            .registerWorkflowImplementationTypes(ExampleWorkflowAPIImpl.class)
            .setActivityImplementations(new ExampleActivityImpl())
            .setDoNotStart(true)
            .build();

//    public TestWorkflowRule testWorkflowRule =
//        TestWorkflowRule.newBuilder()
//            .setWorkflowTypes(ExampleWorkflowAPIImpl.class)
//            .setDoNotStart(true)
//            .build();

    @Test
    public void testIntegrationGetGreeting(TestWorkflowEnvironment testEnv, Worker worker, ExampleWorkflowAPI workflow) {

//
//        ExampleWorkflowAPI workflow =
//            testWorkflowRule
//                .getWorkflowClient()
//                .newWorkflowStub(
//                    ExampleWorkflowAPI.class,
//                    WorkflowOptions.newBuilder().setTaskQueue(testWorkflowRule.getTaskQueue()).build());
        testEnv.start();
        var greeting = workflow.execute(new ExecInput(1, "John"));
        assertThat(greeting).usingRecursiveComparison().ignoringFieldsOfTypes(Instant.class).isEqualTo(new ExecOutput(1, "Processed: John", Instant.now()));
        testEnv.shutdown();
//        testWorkflowRule.getTestEnvironment().shutdown();
    }
}
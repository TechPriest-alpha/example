package com.example.temporal;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ExampleWorkflowAPI {

    @WorkflowMethod
    ExecOutput execute(ExecInput execInput);
}

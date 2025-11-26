package com.example.temporal;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface ExampleActivity {
    @ActivityMethod
    ExecOutput handle(ExecInput execInput);
}

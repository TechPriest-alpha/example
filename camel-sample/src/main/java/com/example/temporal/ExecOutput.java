package com.example.temporal;

import java.time.Instant;

public record ExecOutput(
    int id,
    String outcome,
    Instant time
) {
}

package org.example;

import lombok.experimental.UtilityClass;

import java.util.concurrent.atomic.AtomicBoolean;

@UtilityClass
public class Lifecycle {
    public static final AtomicBoolean RUN = new AtomicBoolean(true);
    public static final AtomicBoolean RESTART = new AtomicBoolean(false);
}

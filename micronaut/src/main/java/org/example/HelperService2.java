package org.example;

import io.micronaut.validation.Validated;

import javax.inject.Singleton;

@Singleton
@Validated
public class HelperService2 implements Api {
    public String output() {
        return "Wow22! ";
    }
}

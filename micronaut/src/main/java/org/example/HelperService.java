package org.example;

import io.micronaut.validation.Validated;

import javax.inject.Singleton;

@Singleton
@Validated
public class HelperService implements Api {
    public String output() {
        return "Wow11! ";
    }
}

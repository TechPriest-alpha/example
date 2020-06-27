package io.example.quarkus.services;

import javax.enterprise.inject.Any;
import javax.inject.Singleton;

@Any
@Singleton
public class ApiImpl3 implements SomeAPI {
    @Override
    public String data() {
        return "3";
    }
}

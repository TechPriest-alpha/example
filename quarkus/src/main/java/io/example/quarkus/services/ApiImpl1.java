package io.example.quarkus.services;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class ApiImpl1 implements SomeAPI {

    @Override
    public String data() {
        return "1";
    }
}

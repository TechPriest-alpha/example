package io.example.quarkus.services;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApiImpl2 implements SomeAPI {
    @Override
    public String data() {
        return "2";
    }
}

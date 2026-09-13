package com.example.spring.study;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class SomeTestAPI {

    @GetMapping("/hello/{value}")
    public String testHello(@Nullable @PathVariable("value") String value) {
        log.info("testHello: {}", value);
        return "Hello " + (value == null ? "" : value);
    }
}

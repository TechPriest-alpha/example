package com.example.spring.modulith.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.spring")
public class MyStudyApp {

    public static void main(String[] args) {
        SpringApplication.run(MyStudyApp.class, args);
    }
}
